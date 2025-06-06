package com.kernel.common.global.security;

import com.kernel.common.customer.entity.Customer;
import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomerUserDetails implements UserDetails, AuthenticatedUser {

    private final Customer customer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        String role = customer.getUserType();

        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role;
            }
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getPhone();
    }

    @Override
    public Long getUserId() {
        return customer.getCustomerId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getName() { return customer.getUserName(); }

    public UserStatus getStatus() { return customer.getStatus(); }
}
