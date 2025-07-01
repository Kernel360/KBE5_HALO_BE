package com.kernel.global.security;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_" + user.getRole().name());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    // 계정ID(phone)
    @Override
    public String getUsername() {
        return this.user.getPhone();
    }

    // 유저ID (PK)
    public Long getUserId() {
        return this.user.getUserId();
    }

    // 유저 정보
    public String getName(){
        return this.user.getUserName();
    }

    // 계정 상태
    public UserStatus getStatus() {
        return this.user.getStatus();
    }

    // 계정 권한
    public UserRole getRole(){
        return this.user.getRole();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
