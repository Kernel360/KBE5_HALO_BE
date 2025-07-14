package com.kernel.global.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel.global.service.GoogleOAuth2Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2FailureHandler implements AuthenticationFailureHandler {

    @Value("${app.frontend-url}")
    private String frontendUrl;

    private final GoogleOAuth2Service googleOAuth2Service;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        Map<String, Object> failureResponse = buildFailureResponse(exception, request);
        throw new RuntimeException(objectMapper.writeValueAsString(failureResponse));
    }

    public Map<String, Object> buildFailureResponse(AuthenticationException exception, HttpServletRequest request) {
        String accessToken = null;
        String errorCode = "oauth_error";
        String errorMessage = exception.getMessage();

        if (exception instanceof OAuth2AuthenticationException oae) {
            errorCode = oae.getError().getErrorCode();
            errorMessage = oae.getMessage();
            accessToken = oae.getError().getDescription();
        }

        if (accessToken != null) {
            if ("DUPLICATE_PHONE".equals(errorCode) || "MISSING_REQUIRED_INFO".equals(errorCode)) {
                googleOAuth2Service.unlinkAccount(accessToken);
                log.info("[OAuth2 Failure] Google account unlinked");
            } else {
                googleOAuth2Service.revokeToken(accessToken);
                log.info("[OAuth2 Failure] Google token revoked");
            }
        }

        String requestUri = request.getRequestURI();
        String role = "UNKNOWN";
        if (requestUri.contains("customer")) {
            role = "CUSTOMER";
        } else if (requestUri.contains("manager")) {
            role = "MANAGER";
        }

        Map<String, Object> failureResponse = new HashMap<>();
        failureResponse.put("error", errorCode);
        failureResponse.put("message", errorMessage);
        failureResponse.put("role", role);

        return failureResponse;
    }
}
