package com.kernel.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:5173") // 3000: React (CRA), 5173: Vite (Vue, React 지원)
                .allowCredentials(true)           // 쿠키 허용(refreshToken)
                .exposedHeaders("Authorization"); // 응답 헤더 노출 허용(accessToken)
    }

}
