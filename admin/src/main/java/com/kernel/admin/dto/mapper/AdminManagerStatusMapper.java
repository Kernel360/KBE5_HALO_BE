package com.kernel.admin.dto.mapper;

import com.kernel.admin.dto.response.AdminManagerStatusResponseDTO;
import com.kernel.manager.entity.Manager;
import org.springframework.stereotype.Component;

@Component
public class AdminManagerStatusMapper {

    // Entity -> AdminManagerStatusResponseDTO
    public AdminManagerStatusResponseDTO toResponseDTO(Manager manager) {
        // TODO: Manger Entity를 Admin이 Manager Status 변경 응답으로 변환하는 로직 구현
        return AdminManagerStatusResponseDTO.builder()
                .managerId(manager.getManagerId())
                .status(manager.getStatus())
                .updatedAt(manager.getUpdatedAt())
                .updatedBy(manager.getUpdatedBy())
                .build();
    }
}
