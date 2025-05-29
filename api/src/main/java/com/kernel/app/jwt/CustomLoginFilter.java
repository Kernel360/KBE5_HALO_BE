package com.kernel.app.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel.app.dto.AdminUserDetails;
import com.kernel.app.dto.CustomerUserDetails;
import com.kernel.app.dto.ManagerUserDetails;
import com.kernel.app.entity.Refresh;
import com.kernel.app.repository.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String APPLICATION_JSON = "application/json";
    private static final String BEARER_PREFIX = "Bearer ";
    public static final int REFRESH_COOKIE_MAX_AGE = 24 * 60 * 60; // 24시간

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RefreshRepository refreshRepository;
    private final JwtProperties jwtProperties;

    // 로그인 시도
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Credentials credentials = extractCredentials(request);
        String userType = extractUserTypeFromUri(request.getRequestURI());

        if (userType == null) {
            throw new AuthenticationServiceException("Invalid login URL: User type could not be determined.");
        }

        String prefixedUsername = userType + ":" + credentials.phone;
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(prefixedUsername, credentials.password);
        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) {

        String userType = extractUserTypeFromUri(request.getRequestURI());

        String phone;
        String role;

        // role 권한에서 추출
        role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new IllegalStateException("No authority found for authenticated user."));

        // userType에 따라 캐스팅 분기
        switch (userType) {
            case "customer" -> {
                CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();
                phone = userDetails.getUsername();
            }
            case "manager" -> {
                ManagerUserDetails userDetails = (ManagerUserDetails) authentication.getPrincipal();
                phone = userDetails.getUsername();
            }
            case "admin" -> {
                AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
                phone = userDetails.getUsername();
            }
            default -> throw new IllegalStateException("Unsupported user type: " + userType);
        }

        String accessToken = jwtTokenProvider.createToken("access", phone, role, jwtProperties.accessTokenValiditySeconds());
        String refreshToken = jwtTokenProvider.createToken("refresh", phone, role, jwtProperties.refreshTokenValiditySeconds());

        saveRefreshToken(phone, refreshToken, jwtProperties.refreshTokenValiditySeconds());

        response.setHeader("Authorization", BEARER_PREFIX + accessToken);
        response.addCookie(createHttpOnlyCookie("refresh", refreshToken));
        response.setStatus(HttpStatus.OK.value());
    }

    // 로그인 실패 시
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    /*** 클래스 내 메서드 ***/

    // 단순 데이터 보관용 객체
    private record Credentials(String phone, String password) {}

    // JSON 파싱 Form-data 파싱
    private Credentials extractCredentials(HttpServletRequest request) {
        try {
            if (request.getContentType() != null && request.getContentType().contains(APPLICATION_JSON)) {
                Map<String, String> requestMap = objectMapper.readValue(request.getInputStream(), Map.class);
                return new Credentials(requestMap.get("phone"), requestMap.get("password"));
            } else {
                return new Credentials(obtainUsername(request), obtainPassword(request));
            }
        } catch (IOException e) {
            throw new AuthenticationServiceException("Failed to parse login credentials", e);
        }
    }

    // uri로 userType 추출
    private String extractUserTypeFromUri(String uri) {
        if (uri.contains("/customers")) return "customer";
        if (uri.contains("/managers")) return "manager";
        if (uri.contains("/admin")) return "admin";
        return null;
    }

    // refreshToken 저장
    private void saveRefreshToken(String phone, String refresh, long expireMs) {
        Date expiration = new Date(System.currentTimeMillis() + expireMs);
        refreshRepository.save(Refresh.builder()
                .phone(phone)
                .refreshToken(refresh)
                .expiration(expiration.toString())
                .build());
    }

    // 쿠키 생성
    private Cookie createHttpOnlyCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(REFRESH_COOKIE_MAX_AGE);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        // cookie.setSecure(true); // TODO true 설정 시, HTTPS에서만 전송됨(http x) 테스트시 용이하게 주석처리. 배포시 설정해야함
        return cookie;
    }

}
