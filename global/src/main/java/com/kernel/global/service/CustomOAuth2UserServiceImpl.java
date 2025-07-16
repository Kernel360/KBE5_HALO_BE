package com.kernel.global.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel.global.common.enums.ErrorCode;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.common.exception.AuthException;
import com.kernel.global.common.handler.CustomOAuth2SuccessHandler;
import com.kernel.global.domain.entity.User;
import com.kernel.global.repository.UserRepository;
import com.kernel.global.service.dto.response.OAuthLoginResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserServiceImpl implements CustomOAuth2UserService {

    private final UserRepository userRepository;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    public OAuthLoginResult login(String code, String role) {
        // 1. WebClient 설정 -> Google OAuth2 토큰 엔드포인트 호출을 위한 WebClient 생성
        WebClient webClient = WebClient.builder()
                .baseUrl("https://oauth2.googleapis.com")
                .build();

        // 2. redirectUri 설정 (role 기반)
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

        // 3. OAuth2 토큰 요청을 위한 폼 데이터 구성
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("grant_type", "authorization_code");

        // 4. Google OAuth2 토큰 엔드포인트 호출
        String responseBody = webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 5. 토큰 응답을 JSON으로 파싱
        Map<String, Object> tokenResponse;
        try {
            tokenResponse = new ObjectMapper()
                    .readValue(responseBody, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse token response from Google", e);
        }

        String accessToken = (String) tokenResponse.get("access_token");

        // 6. accessToken을 사용하여 google에 사용자 정보 요청
        Map<String, Object> userInfo = webClient.get()
                .uri("https://www.googleapis.com/oauth2/v3/userinfo")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");

        // 7. 사용자 정보 유효성 검사
        if (email == null || name == null) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("MISSING_REQUIRED_INFO", accessToken, null),
                    new AuthException(ErrorCode.INVALID_USER_INFO)
            );
        }

        // 8. providerId 기반으로 기존 사용자 조회
        String provider = "GOOGLE";
        String providerId = (String) userInfo.get("sub");
        Optional<User> userOptional = userRepository.findByProviderIdAndStatus(providerId, UserStatus.ACTIVE);

        // 9. 기존 사용자 여부에 따라 응답 처리
        if (userOptional.isPresent()) {
            // 기존 사용자 처리
            User existingUser = userOptional.get();
            return customOAuth2SuccessHandler.buildExistingUserLoginSuccessResponse(
                existingUser.getPhone(),
                existingUser.getUserId(),
                existingUser.getUserName(),
                userOptional.get().getRole().toString(),
                existingUser.getStatus(),
                provider
            );
        } else {
            // 신규 사용자 처리
            userInfo.put("isNew", true);
            return customOAuth2SuccessHandler.buildNewUserResponse(email, name, role, provider, providerId);
        }
    }
}
