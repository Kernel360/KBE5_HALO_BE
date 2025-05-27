package com.kernel.app.jwt;

import com.kernel.app.dto.CustomerUserDetails;
import com.kernel.app.dto.UserInfo;
import com.kernel.app.entity.Admin;
import com.kernel.app.entity.Customer;
import com.kernel.app.entity.Manager;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 Authorization에 담긴 토큰을 꺼냄
        String authorizationHeader = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 Bearer로 시작하지 않으면 다음 필터로 넘김
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Bearer 접두사 제거하여 실제 토큰 추출
        String accessToken = authorizationHeader.substring(7);

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtTokenProvider.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String category = jwtTokenProvider.getCategory(accessToken);

        if (!category.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


        // username, role 값을 획득
        String username = jwtTokenProvider.getUsername(accessToken);
        String role = jwtTokenProvider.getRole(accessToken);

        // role에 따라 userInfo 생성
        UserInfo userInfo = createUserInfo(username, role);
        CustomerUserDetails userDetails = new CustomerUserDetails(userInfo);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

    private UserInfo createUserInfo(String username, String userType) {
        return switch (userType) {
            case "ROLE_CUSTOMER" -> Customer.builder()
                    .email(username)
                    .password("temp")
                    .build();
            case "ROLE_MANAGER" -> Manager.builder()
                    .email(username)
                    .password("temp")
                    .build();
            case "ROLE_ADMIN" -> Admin.builder()
                    .email(username)
                    .password("temp")
                    .build();
            default -> throw new IllegalArgumentException("Unknown user type: " + userType);
        };
    }
}

