package com.kernel.app.config;

import com.kernel.app.enums.UserType;
import com.kernel.app.jwt.CustomLoginFilter;
import com.kernel.app.jwt.CustomLogoutFilter;
import com.kernel.app.jwt.JwtFilter;
import com.kernel.app.jwt.JwtTokenProvider;

import com.kernel.app.repository.RefreshRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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


    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterchain(HttpSecurity http) throws Exception {

        //cors
        http.cors((cors) -> cors
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration config = new CorsConfiguration();
                        // 허용할 프론트엔드 서버
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);

                        config.setExposedHeaders(Collections.singletonList("Authorization"));

                        return config;
                    }
                }));

        //csrf disable : 세션을 stateless 상태로 관리하기 때문에 csrf 공격에 안전하다
        http.csrf((auth) -> auth.disable());

        //Login 방식을 커스텀 할 예정이기 때문에 기존 로그인 방식 두개 disable
        http.formLogin((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());

        // 경로별 인가 작업
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/customers/login", "/api/customers/signup",
                                "/api/managers/**",
                                "/api/admin/**",
                                "/api/reissue",
                                "/error",
                                "/favicon.ico"
                ).permitAll()
                .requestMatchers("/api/customers/**").hasRole(UserType.CUSTOMER.name())
                //.requestMatchers("/api/managers/**").hasRole(UserType.MANAGER.name())
                //.requestMatchers("/api/admin/**").hasRole(UserType.ADMIN.name())
                .anyRequest().anonymous()); //그 외 요청은 모드 접근 가능

        // loginFiilter전에 jwt 확인
        http.addFilterBefore(new JwtFilter(jwtTokenProvider), CustomLoginFilter.class);

        CustomLoginFilter loginFilter = new CustomLoginFilter(jwtTokenProvider, authenticationManager(authenticationConfiguration), refreshRepository);
        loginFilter.setFilterProcessesUrl("/api/customers/login"); // LoginFilter는 기본적으로 '/login' 경로로만 동작하므로 url 설정을 해줘야한다.

        // 기존 권한확인filter 대신 custom한 loginFilter로 권한 확인 진행
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        // 기존 logoutFilter전에 custom한 logoutFilter로 로그아웃
        http.addFilterBefore(new CustomLogoutFilter(jwtTokenProvider, refreshRepository), LogoutFilter.class);

        // 로그아웃 설정
        http.logout(logout ->
                            logout.logoutUrl("/api/logout")
                            .logoutSuccessUrl("/")
                            .deleteCookies("refresh"));

        // 세션 설정
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
