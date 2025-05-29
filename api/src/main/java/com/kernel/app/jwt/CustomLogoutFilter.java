package com.kernel.app.jwt;

import com.kernel.app.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshRepository refreshRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //path and method verify
        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();

        // uri가 /logout으로 끝나지 않거나 POST가 아닌 경우
        if (!requestUri.endsWith("/logout") || !requestMethod.equals("POST")) {

            // 다음 filter 실행 -> logout 진행 x
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = extractRefreshTokenFromCookies(request);
        if (refreshToken == null || isInvalidToken(refreshToken, response)) {
            return;
        }

        // DB에 존재하는지 확인
        if (!refreshRepository.existsByRefreshToken(refreshToken)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        //로그아웃 진행, Refresh 토큰 DB에서 제거
        refreshRepository.deleteByRefreshToken(refreshToken);

        //Refresh 토큰 Cookie 값 0
        Cookie expiredCookie = new Cookie("refresh", null);
        expiredCookie .setMaxAge(0);
        expiredCookie .setPath("/");
        response.addCookie(expiredCookie );

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /*** 클래스 내 메서드 ***/
    // cookies에서 token 추출
    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .map(cookie1 -> cookie1.getValue())
                .findFirst()
                .orElse(null);
    }

    // 토큰 검사
    private boolean isInvalidToken(String refreshToken, HttpServletResponse response) {

        // 만료 검사
        try {
            jwtTokenProvider.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return true;
        }

        // refresh 토큰이 아닐 경우
        if (!"refresh".equals(jwtTokenProvider.getCategory(refreshToken))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return true;
        }

        return false;
    }


}
