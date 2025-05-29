package com.kernel.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class GlobalPaginationConfig implements WebMvcConfigurer {

    /**
     * 전역적으로 Pagination 기본 옵션을 설정합니다.
     * 페이지 크기: 10, 정렬 기준: createdAt 내림차순
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();

        pageableResolver.setFallbackPageable(
                PageRequest.of(0, 10, Sort.by("createdAt").descending())
        );

        resolvers.add(pageableResolver);
    }
}
