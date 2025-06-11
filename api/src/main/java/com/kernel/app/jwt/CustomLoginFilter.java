package com.kernel.app.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel.app.entity.Refresh;
import com.kernel.app.exception.ErrorCode;
import com.kernel.app.exception.dto.ErrorResponse;
import com.kernel.app.repository.RefreshRepository;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.global.security.AdminUserDetails;
import com.kernel.common.global.security.CustomerUserDetails;
import com.kernel.common.global.security.ManagerUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String APPLICATION_JSON = "application/json";
    private static final String BEARER_PREFIX = "Bearer ";
    public static final int REFRESH_COOKIE_MAX_AGE = 24 * 60 * 60; // 24시간

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final RefreshRepository refreshRepository;
    private final JwtProperties jwtProperties;

    // 로그인 시도
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Credentials credentials = extractCredentials(request);
            String userType = extractUserTypeFromUri(request.getRequestURI());

            if (userType == null) {
                throw new AuthenticationServiceException("로그인 URL이 올바르지 않습니다.");
            }

            String prefixedUsername = userType + ":" + credentials.phone;
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(prefixedUsername, credentials.password);

            return authenticationManager.authenticate(authToken);
        }catch (Exception e) {
            log.error("Unexpected error during authentication attempt", e);
            throw new AuthenticationServiceException("로그인 처리 중 오류가 발생했습니다.", e);
        }
    }

    // 로그인 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) {
        try{
            String userType = extractUserTypeFromUri(request.getRequestURI());

        String phone;
        String role;
        Long userId;
        String name;
        UserStatus status;

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
                userId = userDetails.getUserId();
                name = userDetails.getName();
                status = userDetails.getStatus();
            }
            case "manager" -> {
                ManagerUserDetails userDetails = (ManagerUserDetails) authentication.getPrincipal();
                phone = userDetails.getUsername();
                userId = userDetails.getUserId();
                name = userDetails.getName();
                status = userDetails.getStatus();
            }
            case "admin" -> {
                AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
                phone = userDetails.getUsername();
                userId = userDetails.getUserId();
                name = userDetails.getUsername();
                status = userDetails.getStatus();
            }
            default -> throw new IllegalStateException("Unsupported user type: " + userType);
        }

        String accessToken = jwtTokenProvider.createToken("access", phone, userId, role, status.name(), jwtProperties.accessTokenValiditySeconds());
        String refreshToken = jwtTokenProvider.createToken("refresh", phone, userId, role, status.name(), jwtProperties.refreshTokenValiditySeconds());

        saveRefreshToken(phone, refreshToken, jwtProperties.refreshTokenValiditySeconds());

            // 성공 응답
            response.setHeader("Authorization", BEARER_PREFIX + accessToken);
            response.addCookie(createHttpOnlyCookie("refresh", refreshToken));
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=UTF-8");

            // Map 생성해서 name 담기
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("userName", name);
            bodyMap.put("status", status);

            ApiResponse<Map<String, Object>> successResponse = new ApiResponse<>(true, "로그인이 완료되었습니다.", bodyMap);
            objectMapper.writeValue(response.getWriter(), successResponse);

            log.info("User logged in successfully: {}", phone);

        }catch (Exception e) {
            log.error("Error during successful authentication processing", e);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    // 로그인 실패 시
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {

        log.warn("Authentication failed: {}", failed.getMessage());

        ErrorResponse errorResponse;
        if (failed instanceof BadCredentialsException) {
            errorResponse = new ErrorResponse(ErrorCode.INVALID_CREDENTIALS);
        } else if (failed instanceof AccountExpiredException) {
            errorResponse = new ErrorResponse(ErrorCode.ACCOUNT_LOCKED);
        } else {
            errorResponse = new ErrorResponse(ErrorCode.INVALID_CREDENTIALS);
        }

        // Spring이 자동으로 JSON 변환하도록 처리
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        log.debug("Error response JSON: {}", jsonResponse);

        response.getWriter().print(jsonResponse); // write 대신 print 사용
        response.getWriter().flush();
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
