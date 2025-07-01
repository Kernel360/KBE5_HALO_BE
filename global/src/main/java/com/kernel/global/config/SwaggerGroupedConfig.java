package com.kernel.global.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerGroupedConfig {

    // Global API 그룹핑 설정
    @Bean
    public GroupedOpenApi globalApi() {
        return GroupedOpenApi.builder()
                .group("Global")
                .packagesToScan("com.kernel.global.controller")
                .build();
    }

    // Member API 그룹핑 설정
    @Bean
    public GroupedOpenApi memberApi() {
        return GroupedOpenApi.builder()
                .group("Member")
                .packagesToScan("com.kernel.member.controller")
                .build();
    }

    // Admin API 그룹핑 설정
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("Admin")
                .packagesToScan("com.kernel.admin.controller")
                .build();
    }

    // Reservation API 그룹핑 설정
    @Bean
    public GroupedOpenApi reservationApi() {
        return GroupedOpenApi.builder()
                .group("Reservation")
                .packagesToScan("com.kernel.reservation.controller")
                .build();
    }

    // Payment API 그룹핑 설정
    @Bean
    public GroupedOpenApi paymentApi() {
        return GroupedOpenApi.builder()
                .group("Payment")
                .packagesToScan("com.kernel.payment.controller")
                .build();
    }

    // Evaluation API 그룹핑 설정
    @Bean
    public GroupedOpenApi evaluationApi() {
        return GroupedOpenApi.builder()
                .group("Evaluation")
                .packagesToScan("com.kernel.evaluation.controller")
                .build();
    }

    // Inquiry API 그룹핑 설정
    @Bean
    public GroupedOpenApi inquiryApi() {
        return GroupedOpenApi.builder()
                .group("Inquiry")
                .packagesToScan("com.kernel.inquiry.controller")
                .build();
    }
}
