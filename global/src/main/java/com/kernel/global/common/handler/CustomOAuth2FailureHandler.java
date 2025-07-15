package com.kernel.global.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel.global.service.GoogleOAuth2Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2FailureHandler implements AuthenticationFailureHandler {

    private final GoogleOAuth2Service googleOAuth2Service;

    @Autowired
    private ObjectMapper objectMapper;

    // 인증 실패 시 호출되는 메서드
    // 예외 메시지를 포함한 JSON 문자열을 RuntimeException으로 throw
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        Map<String, Object> failureResponse = buildFailureResponse(exception, request);
        throw new RuntimeException(objectMapper.writeValueAsString(failureResponse));
    }

    // 인증 실패에 대한 응답 데이터를 구성하는 메서드
    // - 오류 코드와 메시지를 추출
    // - 상황에 따라 Google 계정을 연동 해제하거나 토큰을 폐기
    // - 요청 URI에 따라 사용자 역할을 추론
    public Map<String, Object> buildFailureResponse(AuthenticationException exception, HttpServletRequest request) {
        String accessToken = null;
        String errorCode = "oauth_error";
        String errorMessage = exception.getMessage();

        if (exception instanceof OAuth2AuthenticationException oae) {
            errorCode = oae.getError().getErrorCode();
            errorMessage = oae.getMessage();
            accessToken = oae.getError().getDescription();
        }

        // Google 계정과 관련된 실패 상황에 따라 계정 연동 해제 또는 토큰 폐기
        if (accessToken != null) {
            if ("DUPLICATE_PHONE".equals(errorCode) || "MISSING_REQUIRED_INFO".equals(errorCode)) {
                googleOAuth2Service.unlinkAccount(accessToken);
                log.info("[OAuth2 Failure] Google account unlinked");
            } else {
                googleOAuth2Service.revokeToken(accessToken);
                log.info("[OAuth2 Failure] Google token revoked");
            }
        }

        // 요청 URI를 기반으로 사용자 역할(CUSTOMER, MANAGER, UNKNOWN)을 추론
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
