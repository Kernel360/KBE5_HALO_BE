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

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2SuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshRepository refreshRepository;

    // 신규 소셜 로그인 사용자를 위한 응답 객체 생성
    public OAuthLoginResult buildNewUserResponse(String email, String name, String role, String provider, String providerId) {
        // 신규 사용자의 경우, providerId는 필수로 제공되어야 함
        OAuthLoginRspDTO result = OAuthLoginRspDTO.ofNewUser(email, name, role, provider, providerId);

        // 액세스 토큰 및 쿠키는 생성하지 않음 (신규 사용자는 추후 추가 정보 입력 필요)
        return OAuthLoginResult.ofResult(result, null, null);
    }

    // 기존 소셜 로그인 사용자를 위한 응답 객체 생성
    // JWT 토큰 생성 및 refresh 토큰을 DB와 쿠키에 저장
    public OAuthLoginResult buildExistingUserLoginSuccessResponse(String phone, Long userId, String name, String role, UserStatus status, String provider) {
        String accessToken = jwtTokenProvider.createToken("access", phone, userId, role, status.name(), jwtProperties.accessTokenValiditySeconds());
        String refreshToken = jwtTokenProvider.createToken("refresh", phone, userId, role, status.name(), jwtProperties.refreshTokenValiditySeconds());

        saveRefreshToken(phone, refreshToken, jwtProperties.refreshTokenValiditySeconds());

        Cookie refreshCookie = createHttpOnlyCookie("refresh", refreshToken);

        OAuthLoginRspDTO result = OAuthLoginRspDTO.ofExistingUser(name, role, phone, userId, status, provider);

        return OAuthLoginResult.ofResult(result, accessToken, refreshCookie);
    }

    // 생성된 refresh 토큰을 DB에 저장
    // 만료 시간은 현재 시간 + 유효 시간(milliseconds)
    private void saveRefreshToken(String phone, String refresh, long expireMs) {
        Date expiration = new Date(System.currentTimeMillis() + expireMs);
        refreshRepository.save(Refresh.builder()
                .phone(phone)
                .refreshToken(refresh)
                .refreshToken(refresh)
                .expiration(expiration.toString())
                .build());
    }

    // HttpOnly 속성을 갖는 refresh 토큰 쿠키 생성
    private Cookie createHttpOnlyCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(jwtProperties.refreshTokenValiditySeconds().intValue());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // TODO true 설정 시, HTTPS에서만 전송됨(http x) 테스트시 용이하게 주석처리. 배포시 설정해야함
        return cookie;
    }
}
