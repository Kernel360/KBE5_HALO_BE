package com.kernel.global.jwt;

import com.kernel.global.common.constant.SecurityUrlConstants;
import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import com.kernel.global.security.CustomUserDetails;
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

        // 토큰이 없는 요청은 JWT 필터에서 제외
        String uri = request.getRequestURI();
        if (SecurityUrlConstants.JWT_FILTER_EXCLUDE_PATHS.contains(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Bearer 접두사 제거하여 실제 토큰 추출
        // 경로에 따라서 분기 필요
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
        Long userId = jwtTokenProvider.getUserId(accessToken);
        String status = jwtTokenProvider.getStatus(accessToken);
        String role = jwtTokenProvider.getRole(accessToken);

        // UserDetails 객체 생성
        UserDetails userDetails = createUserDetailsFromToken(phone, userId, status, role);

        // 인증 객체 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

    // 로그인 유저 UserDetails 객체 생성
    private UserDetails createUserDetailsFromToken(String phone, Long userId, String status, String role) {

        String formattedRole = role.replace("ROLE_", "");

        User user = User.builder()
                .userId(userId)
                .phone(phone)
                .status(UserStatus.valueOf(status))
                .role(UserRole.valueOf(formattedRole))
                .build();

        return new CustomUserDetails(user);
    }
}

