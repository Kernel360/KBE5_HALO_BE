package com.kernel.app.dto.mapper;

import com.kernel.common.admin.dto.AdminSignupReqDTO;
import com.kernel.common.admin.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAuthMapper {

   private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // RequestDTO -> Entity
    public Admin toEntity(AdminSignupReqDTO dto){
        return Admin.builder()
                .phone(dto.getPhone())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .status(dto.getStatus())
                .build();
    }

}
