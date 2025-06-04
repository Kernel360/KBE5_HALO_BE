package com.kernel.common.global;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthenticatedUser {
    String getUsername();
    Long getUserId();
    Collection<? extends GrantedAuthority> getAuthorities();
}