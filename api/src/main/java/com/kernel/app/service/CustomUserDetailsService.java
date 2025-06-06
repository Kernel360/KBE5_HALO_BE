package com.kernel.app.service;

import com.kernel.app.repository.AdminAuthRepository;
import com.kernel.app.repository.CustomerAuthRepository;
import com.kernel.app.repository.ManagerAuthRepository;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.global.security.AdminUserDetails;
import com.kernel.common.global.security.CustomerUserDetails;
import com.kernel.common.global.security.ManagerUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerAuthRepository customerRepository;
    private final ManagerAuthRepository managerRepository;
    private final AdminAuthRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String prefixedUsername) throws UsernameNotFoundException {

        String[] parts = prefixedUsername.split(":", 2);
        if(parts.length != 2) {
            throw new UsernameNotFoundException("Invalid username format");
        }

        String userType = parts[0];
        String phone = parts[1];

        return switch (userType.toLowerCase()) {
            case "customer" -> customerRepository.findByPhone(phone)
                    .map(CustomerUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
            case "manager" -> managerRepository.findByPhoneAndStatusIn(phone, List.of(UserStatus.ACTIVE, UserStatus.PENDING, UserStatus.REJECTED, UserStatus.TERMINATION_PENDING))
                    .map(ManagerUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("Manager not found"));
            case "admin" -> adminRepository.findByPhone(phone)
                    .map(AdminUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
            default -> throw new UsernameNotFoundException("Invalid user type: " + userType);
        };

    }
}

