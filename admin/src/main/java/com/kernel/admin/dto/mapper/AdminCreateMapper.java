package com.kernel.admin.dto.mapper;

import org.springframework.stereotype.Component;

@Component
public class AdminCreateMapper {
    // AdminCreateRequsetDTO -> Admin Entity
    public class Admin toCreateAdminEntity(AdminCreateRequestDTO request) {
        // TODO: request DTO를 Admin Entity로 변환하는 로직을 작성
        return Admin.builder()
                .id(request.getId())
                .password(request.getPassword())
                .build();
    }
}
