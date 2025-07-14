package com.kernel.global.service;

import com.kernel.global.common.enums.ErrorCode;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.common.exception.AuthException;
import com.kernel.global.common.handler.CustomOAuth2SuccessHandler;
import com.kernel.global.domain.entity.User;
import com.kernel.global.repository.UserRepository;
import com.kernel.global.service.dto.response.OAuthLoginResult;
import com.kernel.global.service.dto.response.OAuthLoginRspDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserServiceImpl implements CustomOAuth2UserService {

    private final UserRepository userRepository;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    public OAuthLoginResult login(String code, String role) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://oauth2.googleapis.com")
                .build();

        String redirectUri;
        if ("customer".equalsIgnoreCase(role)) {
            redirectUri = System.getenv("GOOGLE_REDIRECT_URI_CUSTOMER");
        } else if ("manager".equalsIgnoreCase(role)) {
            redirectUri = System.getenv("GOOGLE_REDIRECT_URI_MANAGER");
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        String clientId = System.getenv("GOOGLE_CLIENT_ID");
        String clientSecret = System.getenv("GOOGLE_CLIENT_SECRET");

        System.out.println("Redirect URI: " + redirectUri);
        System.out.println("Client ID: " + clientId);
        System.out.println("Client Secret: " + clientSecret);
        System.out.println("Code: " + code);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("grant_type", "authorization_code");

        String responseBody = webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Parse the responseBody as JSON to Map
        Map<String, Object> tokenResponse;
        try {
            tokenResponse = new com.fasterxml.jackson.databind.ObjectMapper()
                    .readValue(responseBody, Map.class);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Failed to parse token response from Google", e);
        }

        String accessToken = (String) tokenResponse.get("access_token");

        Map<String, Object> userInfo = webClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        if (email == null || name == null) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("MISSING_REQUIRED_INFO", accessToken, null),
                    new AuthException(ErrorCode.INVALID_USER_INFO)
            );
        }

        Optional<User> userOptional = userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE);

        if (userOptional.isPresent()) {
            // 기존 사용자 처리
            User existingUser = userOptional.get();
            return customOAuth2SuccessHandler.buildLoginSuccessResponse(
                existingUser.getPhone(),
                existingUser.getUserId(),
                existingUser.getUserName(),
                role.toUpperCase(),
                existingUser.getStatus()
            );
        }

        userInfo.put("isNew", true);
        return customOAuth2SuccessHandler.buildSuccessResponse(email, name, role.toUpperCase(), true);
    }
}
