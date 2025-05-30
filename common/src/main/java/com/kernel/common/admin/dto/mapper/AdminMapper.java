package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.response.AdminRspDTO;
import com.kernel.common.admin.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AdminMapper {
    // Admin Entity와 DTO 간의 변환 메서드를 정의합니다.
    public AdminRspDTO toAdminResponseDTO(Admin admin) {
        return AdminRspDTO.builder()
                .adminId(admin.getAdminId())
                .build();
    }
}
