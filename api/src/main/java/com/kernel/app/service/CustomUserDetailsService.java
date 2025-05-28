package com.kernel.app.service;


import com.kernel.app.dto.CustomerUserDetails;
import com.kernel.app.dto.UserInfo;
import com.kernel.app.repository.AdminAuthRepository;
import com.kernel.app.repository.CustomerAuthRepository;
import com.kernel.app.repository.ManagerAuthRepository;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String[] parts = username.split(":", 2);
        if(parts.length != 2) {
            throw new UsernameNotFoundException("Invalid username format");
        }

        String userType = parts[0];
        String phone = parts[1];

        UserInfo userinfo = switch(userType){
            case "customer" -> customerRepository.findByPhone(phone)
                    .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
            case "manager" -> managerRepository.findByPhone(phone)
                    .orElseThrow(() -> new UsernameNotFoundException("Manager not found"));
            case "admin" -> adminRepository.findByPhone(phone)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
            default -> throw new UsernameNotFoundException("Invalid username format");
        };

        return new CustomerUserDetails(userinfo);
    }
}

