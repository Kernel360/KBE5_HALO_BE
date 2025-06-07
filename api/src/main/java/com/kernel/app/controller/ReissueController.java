package com.kernel.app.controller;

import com.kernel.app.entity.Refresh;
import com.kernel.app.jwt.JwtProperties;
import com.kernel.app.jwt.JwtTokenProvider;
import com.kernel.app.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.Date;

import static com.kernel.app.jwt.CustomLoginFilter.REFRESH_COOKIE_MAX_AGE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReissueController {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshRepository refreshRepository;
    private final JwtProperties jwtProperties;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = extractRefreshTokenFromCookies(request);
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("refresh token is missing");
        }

        if (isInvalidRefreshToken(refreshToken, response)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid or expired refresh token");
        }

        String phone = jwtTokenProvider.getUsername(refreshToken);
        String role = jwtTokenProvider.getRole(refreshToken);
        Long userId = jwtTokenProvider.getUserId(refreshToken);
        String status = jwtTokenProvider.getStatus(refreshToken);

        // refresh 토큰 재발급
        String newAccessToken = jwtTokenProvider.createToken("access", phone, userId, role, status, jwtProperties.accessTokenValiditySeconds());
        String newRefreshToken = jwtTokenProvider.createToken("refresh", phone, userId, role, status, jwtProperties.refreshTokenValiditySeconds());

        // 기존 리프레시 삭제 후 새 토큰 저장
        refreshRepository.deleteByRefreshToken(refreshToken);
        saveNewRefreshToken(phone, newRefreshToken, jwtProperties.refreshTokenValiditySeconds());

        // 응답 헤더 및 쿠키 설정
        response.setHeader("Authorization", "Bearer " + newAccessToken);
        response.addCookie(createHttpOnlyCookie("refresh", newRefreshToken));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*** 클래스 내 메서드 ***/
    // cookie에서 토큰 추출
    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .map(cookie1 -> cookie1.getValue())
                .findFirst()
                .orElse(null);
    }

    // refresh 토큰 만료 && refresh 토큰 체크
    private boolean isInvalidRefreshToken(String refreshToken, HttpServletResponse response) {
        try {
            jwtTokenProvider.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            return true;
        }

        return !"refresh".equals(jwtTokenProvider.getCategory(refreshToken));
    }

    // 새로운 토큰 발급
    private void saveNewRefreshToken(String phone, String refresh, Long expirySeconds) {
        Date expiration = new Date(System.currentTimeMillis() + expirySeconds);

        Refresh entity = Refresh.builder()
                .phone(phone)
                .refreshToken(refresh)
                .expiration(expiration.toString())
                .build();

        refreshRepository.save(entity);
    }

    private Cookie createHttpOnlyCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(REFRESH_COOKIE_MAX_AGE);
        // cookie.setSecure(true); // TODO true 설정 시, HTTPS에서만 전송됨(http x) 테스트시 용이하게 주석처리. 배포시 설정해야함
        return cookie;
    }
}
