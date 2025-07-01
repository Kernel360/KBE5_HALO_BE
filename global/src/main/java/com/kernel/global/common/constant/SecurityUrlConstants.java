package com.kernel.global.common.constant;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class SecurityUrlConstants  {

    // 인증 불필요 (모든 사용자 접근 가능)
    public static final String[] PUBLIC_URLS = {
            "/api/customers/auth/signup",
            "/api/managers/auth/signup",
            "/api/common/recovery-id",
            "/api/common/recovery-pwd",
            "/swagger-ui/**",
            "/v1/api-docs/**",
            "swagger-resources/**",
    };

    // 인증 필요 (로그인한 모든 사용자)
    public static final String[] AUTHENTICATED_URLS = {
            "/api/logout",

            "/api/files/**",
            "/api/auth/password-check",
            "/api/auth/reset-pwd",
            "/api/auth/my"
    };

    // 역할별 URL 패턴
    public static final String[] CUSTOMER_URLS = {"/api/customers/**"};
    public static final String[] MANAGER_URLS = {"/api/managers/**"};
    public static final String[] ADMIN_URLS = {"/api/admin/**"};

    // 인증 불필요 (모든 사용자 접근 가능)
    public static final String[] JWT_FILTER_EXCLUDE_URLS = {
            "/api/customers/auth/login",
            "/api/customers/auth/signup",
            "/api/managers/auth/login",
            "/api/managers/auth/signup",
            "/api/admin/auth/login",
            "/api/common/recovery-id",
            "/api/common/recovery-pwd"
    };

    // JWT 필터 제외 URL
    public static final Set<String> JWT_FILTER_EXCLUDE_PATHS = Set.of(JWT_FILTER_EXCLUDE_URLS);

    // 전체 관리 URL 조회 메서드
    public static String[] getAllManagedUrls() {
        return Stream.of(PUBLIC_URLS, AUTHENTICATED_URLS, CUSTOMER_URLS, MANAGER_URLS, ADMIN_URLS)
                .flatMap(Arrays::stream)
                .distinct()
                .toArray(String[]::new);
    }
}
