package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.response.AdminResponseDTO;
import com.kernel.common.admin.entity.Admin;

public class AdminMapper {
    // Admin Entity와 DTO 간의 변환 메서드를 정의합니다.
    public AdminResponseDTO toAdminResponseDTO(Admin admin) {
        return AdminResponseDTO.builder()
                .adminId(admin.getAdminId())
                .build();
    }
}
