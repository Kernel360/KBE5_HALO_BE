package com.kernel.app.config;

import com.kernel.app.exception.handler.JwtAccessDeniedHandler;
import com.kernel.app.exception.handler.JwtAuthenticationEntryPoint;
import com.kernel.app.jwt.*;

import com.kernel.app.repository.RefreshRepository;
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

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshRepository refreshRepository;
    private final JwtProperties jwtProperties;
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

        http
                .securityMatcher("/api/logout", "/api/reissue")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/logout","/api/reissue").permitAll())
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
                .addFilterBefore(new CustomLogoutFilter(jwtTokenProvider, refreshRepository), LogoutFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("refresh"));

        return http.build();
    }

    // CUSTOMER 전용 필터체인
    @Bean
    @Order(1)
    public SecurityFilterChain customerFilterChain(HttpSecurity http) throws Exception {

        applyCommonSecurityConfig(http);

        // 로그인 필터
        CustomLoginFilter loginFilter = new CustomLoginFilter(jwtTokenProvider, authenticationManager(), refreshRepository, jwtProperties);
        loginFilter.setFilterProcessesUrl("/api/customers/login");

        http
                .securityMatcher("/api/customers/**", "/api/customers/login", "/api/reissue")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/customers/**").permitAll()) // TODO 테스트용
                        /*
                        .requestMatchers("/api/customers/login", "/api/customers/signup").permitAll()
                        .requestMatchers("/api/customers/**").hasRole(UserType.CUSTOMER.name())) */ //TODO 테스트용이하게 막아둠, 배포시 주석 제거
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

        CustomLoginFilter loginFilter = new CustomLoginFilter(jwtTokenProvider, authenticationManager(), refreshRepository, jwtProperties);
        loginFilter.setFilterProcessesUrl("/api/managers/login");

        http
                .securityMatcher("/api/managers/**", "/api/managers/login")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/managers/**").permitAll()) // TODO 테스트용
                        /*.requestMatchers("/api/managers/login", "/api/managers/signup").permitAll()
                        .requestMatchers("/api/managers/**").hasRole(UserType.MANAGER.name()))*/ //TODO 테스트용이하게 막아둠, 배포시 주석 제거
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

        CustomLoginFilter loginFilter = new CustomLoginFilter(jwtTokenProvider, authenticationManager(), refreshRepository, jwtProperties);
        loginFilter.setFilterProcessesUrl("/api/admin/login");

        http
                .securityMatcher("/api/admin/**", "/api/admin/login")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").permitAll()) // TODO 테스트용
                       /* .requestMatchers("/api/admin/login", "/api/admin/signup").permitAll()
                        .requestMatchers("/api/admin/**").hasRole(UserType.ADMIN.name())) */ //TODO 테스트용이하게 막아둠, 배포시 주석 제거
                .addFilterBefore(new JwtFilter(jwtTokenProvider), CustomLoginFilter.class)
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomLogoutFilter(jwtTokenProvider, refreshRepository), LogoutFilter.class);


        return http.build();
    }

    //cors 설정 분리
    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setExposedHeaders(Collections.singletonList("Authorization"));
            config.setMaxAge(3600L);
            return config;
        };
    }

    // 공통 Security 설정 (private 메서드로 복원)
    private HttpSecurity applyCommonSecurityConfig(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
    }
}
