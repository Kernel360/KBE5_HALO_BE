package com.kernel.common.global;

import com.kernel.common.global.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AuthenticatedUser {
    String getUsername();
    Long getUserId();
    UserStatus getStatus();
    Collection<? extends GrantedAuthority> getAuthorities();
}