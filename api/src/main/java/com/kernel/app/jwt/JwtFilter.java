package com.kernel.app.jwt;

import com.kernel.common.global.security.AdminUserDetails;
import com.kernel.common.global.security.CustomerUserDetails;
import com.kernel.common.global.security.ManagerUserDetails;
import com.kernel.common.admin.entity.Admin;
import com.kernel.common.manager.entity.Manager;
import com.kernel.common.customer.entity.Customer;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Bearer 접두사 제거하여 실제 토큰 추출
        String accessToken = jwtTokenProvider.resolveToken(request);

        if (accessToken == null || accessToken.isBlank()) {
            // 토큰이 없으면 인증 시도하지 않고 바로 다음 필터로 넘김
            filterChain.doFilter(request, response);
            return;
        }

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
        String phone = jwtTokenProvider.getUsername(accessToken);
        String role = jwtTokenProvider.getRole(accessToken);
        String userId = jwtTokenProvider.getUserId(accessToken);

        // 역할에 따라 적절한 UserDetails 객체 생성
        UserDetails userDetails = createUserDetailsFromToken(phone, role, userId);

        // 인증 객체 생성 (UserDetails 없이도 가능)
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }
    private UserDetails createUserDetailsFromToken(String phone, String role, String userId) {
        // 역할에 따라 적절한 UserDetails 생성
        // 실제로는 최소한의 정보만으로 객체를 생성
        switch (role) {
            case "ROLE_CUSTOMER":
                Customer customer = Customer.builder()
                        .customerId(Long.parseLong(userId))
                        .phone(phone)
                        .build();
                return new CustomerUserDetails(customer);

            case "ROLE_MANAGER":
                Manager manager = Manager.builder()
                        .managerId(Long.parseLong(userId))
                        .phone(phone)
                        .build();
                return new ManagerUserDetails(manager);

            case "ROLE_ADMIN":
                Admin admin = Admin.builder()
                        .adminId(Long.parseLong(userId))
                        .phone(phone)
                        .build();
                return new AdminUserDetails(admin);

            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}

