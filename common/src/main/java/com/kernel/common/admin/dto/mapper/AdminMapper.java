package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.response.AdminRspDTO;
import com.kernel.common.admin.entity.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminMapper {

    // Admin Entity -> AdminRspDTO
    public AdminRspDTO toAdminRspDTO(Admin admin) {
        return AdminRspDTO.builder()
                .adminId(admin.getAdminId())
                .build();
    }
}
