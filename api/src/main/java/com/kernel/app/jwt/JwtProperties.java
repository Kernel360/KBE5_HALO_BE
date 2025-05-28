package com.kernel.app.jwt;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secret,
        Long accessTokenValiditySeconds,
        Long refreshTokenValiditySeconds
) {}