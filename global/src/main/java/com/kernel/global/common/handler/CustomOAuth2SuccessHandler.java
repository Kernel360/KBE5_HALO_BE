package com.kernel.global.common.handler;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.common.properties.JwtProperties;
import com.kernel.global.domain.entity.Refresh;
import com.kernel.global.jwt.JwtTokenProvider;
import com.kernel.global.repository.RefreshRepository;
import com.kernel.global.service.dto.response.OAuthLoginResult;
import com.kernel.global.service.dto.response.OAuthLoginRspDTO;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2SuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshRepository refreshRepository;

    public OAuthLoginResult buildSuccessResponse(String email, String name, String role, boolean isNew) {

        OAuthLoginRspDTO result = OAuthLoginRspDTO.ofNewUser(email, name, role, generateRandomPassword());

        return OAuthLoginResult.ofResult(result, null, null);
    }

    public OAuthLoginResult buildLoginSuccessResponse(String phone, Long userId, String name, String role, UserStatus status) {
        String accessToken = jwtTokenProvider.createToken("access", phone, userId, role, status.name(), jwtProperties.accessTokenValiditySeconds());
        String refreshToken = jwtTokenProvider.createToken("refresh", phone, userId, role, status.name(), jwtProperties.refreshTokenValiditySeconds());

        saveRefreshToken(phone, refreshToken, jwtProperties.refreshTokenValiditySeconds());

        Cookie refreshCookie = createHttpOnlyCookie("refresh", refreshToken);

        OAuthLoginRspDTO result = OAuthLoginRspDTO.ofExistingUser(name, role, phone, userId, status);

        return OAuthLoginResult.ofResult(result, accessToken, refreshCookie);
    }

    // refreshToken 저장
    private void saveRefreshToken(String phone, String refresh, long expireMs) {
        Date expiration = new Date(System.currentTimeMillis() + expireMs);
        refreshRepository.save(Refresh.builder()
                .phone(phone)
                .refreshToken(refresh)
                .refreshToken(refresh)
                .expiration(expiration.toString())
                .build());
    }

    // 쿠키 생성
    private Cookie createHttpOnlyCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(jwtProperties.refreshTokenValiditySeconds().intValue());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // TODO true 설정 시, HTTPS에서만 전송됨(http x) 테스트시 용이하게 주석처리. 배포시 설정해야함
        return cookie;
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();
        java.security.SecureRandom random = new java.security.SecureRandom();
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
}
