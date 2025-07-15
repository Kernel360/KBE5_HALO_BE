package com.kernel.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kernel.global.common.constant.SecurityUrlConstants;
import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.handler.JwtAccessDeniedHandler;
import com.kernel.global.common.handler.JwtAuthenticationEntryPoint;
import com.kernel.global.common.properties.JwtProperties;
import com.kernel.global.jwt.JwtFilter;
import com.kernel.global.jwt.JwtTokenProvider;
import com.kernel.global.repository.RefreshRepository;
import com.kernel.global.security.CustomLoginFilter;
import com.kernel.global.security.CustomLogoutFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguration 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshRepository refreshRepository;
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;
    // 예외 처리
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    // 공통 Security 설정 분리
    @Bean
    @Order(0)
    public SecurityFilterChain commonFilterChain(HttpSecurity http) throws Exception {

        String[] commonUrls = Stream.concat(
                Arrays.stream(SecurityUrlConstants.PUBLIC_URLS),
                Arrays.stream(SecurityUrlConstants.AUTHENTICATED_URLS)
        ).toArray(String[]::new);

        http
            .securityMatcher(commonUrls)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(SecurityUrlConstants.PUBLIC_URLS).permitAll()
                    .requestMatchers(SecurityUrlConstants.AUTHENTICATED_URLS).authenticated()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // 예외 핸들러 추가
            .exceptionHandling(exceptions -> exceptions
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // JWT 인증 필터: 요청이 authorizeHttpRequests()의 인증/권한 체크 전에 실행되어 SecurityContextHolder에 인증 객체를 설정
            .addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new CustomLogoutFilter(jwtTokenProvider, refreshRepository), LogoutFilter.class)
            .logout(logout -> logout
                    .logoutUrl("/api/logout")
//                    .logoutSuccessUrl("/") // 권한에 따라 로그아웃 후 리다이렉트 되는 경로가 달라 프론트에서 처리
                    .deleteCookies("refresh"));

        return http.build();
    }

    // CUSTOMER 전용 필터체인
    @Bean
    @Order(1)
    public SecurityFilterChain customerFilterChain(HttpSecurity http) throws Exception {

        applyCommonSecurityConfig(http);

        // 로그인 필터
        CustomLoginFilter loginFilter = new CustomLoginFilter(jwtTokenProvider, authenticationManager(), objectMapper, refreshRepository, jwtProperties);
        loginFilter.setFilterProcessesUrl("/api/customers/auth/login");

        http
            .securityMatcher(SecurityUrlConstants.CUSTOMER_URLS)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/customers/auth/login", "/api/customers/auth/signup", "/api/customers/auth/google").permitAll()
                    .requestMatchers(SecurityUrlConstants.CUSTOMER_URLS).hasRole(UserRole.CUSTOMER.name()))
            .addFilterBefore(new JwtFilter(jwtTokenProvider), CustomLoginFilter.class)
            .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new CustomLogoutFilter(jwtTokenProvider, refreshRepository), LogoutFilter.class);

        return http.build();
    }

    // MANAGER 전용 필터체인
    @Bean
    @Order(2)
    public SecurityFilterChain managerFilterChain(HttpSecurity http) throws Exception {

        applyCommonSecurityConfig(http);

        CustomLoginFilter loginFilter = new CustomLoginFilter(jwtTokenProvider, authenticationManager(), objectMapper, refreshRepository, jwtProperties);
        loginFilter.setFilterProcessesUrl("/api/managers/auth/login");

        http
            .securityMatcher(SecurityUrlConstants.MANAGER_URLS)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/managers/auth/login", "/api/managers/auth/signup", "/api/managers/auth/google").permitAll()
                    .requestMatchers(SecurityUrlConstants.MANAGER_URLS).hasRole(UserRole.MANAGER.name()))
            .addFilterBefore(new JwtFilter(jwtTokenProvider), CustomLoginFilter.class)
            .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new CustomLogoutFilter(jwtTokenProvider, refreshRepository), LogoutFilter.class);

        return http.build();
    }

    // ADMIN 전용 필터체인
    @Bean
    @Order(3)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {

        applyCommonSecurityConfig(http);

        CustomLoginFilter loginFilter = new CustomLoginFilter(jwtTokenProvider, authenticationManager(), objectMapper, refreshRepository, jwtProperties);
        loginFilter.setFilterProcessesUrl("/api/admin/auth/login");

        http
            .securityMatcher(SecurityUrlConstants.ADMIN_URLS)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/admin/auth/login").permitAll()
                    .requestMatchers(SecurityUrlConstants.ADMIN_URLS).hasRole(UserRole.ADMIN.name()))
            .addFilterBefore(new JwtFilter(jwtTokenProvider), CustomLoginFilter.class)
            .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new CustomLogoutFilter(jwtTokenProvider, refreshRepository), LogoutFilter.class);


        return http.build();
    }

    //cors 설정 분리
    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173", "https://halocare.site", "https://www.halocare.site", "https://api.halocare.site")); // 3000: React (CRA), 5173: Vite (Vue, React 지원)
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setExposedHeaders(Collections.singletonList("Authorization"));
            config.setMaxAge(3600L);
            return config;
        };
    }

    // 공통 Security 설정
    private HttpSecurity applyCommonSecurityConfig(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
    }
}
