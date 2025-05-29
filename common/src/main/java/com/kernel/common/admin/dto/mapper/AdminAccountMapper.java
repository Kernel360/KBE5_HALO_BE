package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.request.AdminAccountReqDTO;
import com.kernel.common.admin.dto.response.AdminPasswordResDTO;
import com.kernel.common.admin.dto.response.AdminResDTO;
import com.kernel.common.admin.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminAccountMapper {

    /**
     * AdminAccountReqDTO에서 Admin 엔티티로 변환합니다.
     *
     * @param request 변환할 AdminAccountReqDTO 객체
     * @return 변환된 Admin 엔티티 객체
     */
    public Admin toCreateAdminEntity(AdminAccountReqDTO request) {
        return Admin.builder()
                .id(request.getId())
                .password(request.getPassword())
                .build();
    }

    /**
     * Admin 엔티티에서 AdminPasswordResDTO로 변환합니다.
     *
     * @param admin 변환할 Admin 엔티티 객체
     * @return 변환된 AdminPasswordResDTO 객체
     */
    public AdminPasswordResDTO toAdminPasswordResponseDTO(Admin admin) {
        return AdminPasswordResDTO.builder()
                .password(admin.getPassword())
                .build();
    }

    /**
     * Admin 엔티티에서 AdminResDTO로 변환합니다.
     *
     * @param admin 변환할 Admin 엔티티 객체
     * @return 변환된 AdminResDTO 객체
     */
    public AdminResDTO toAdminResponseDTO(Admin admin) {
        return AdminResDTO.builder()
                .id(admin.getId())  // 관리자 로그인용 id
                .createdBy(admin.getCreatedBy())
                .createdAt(admin.getCreatedAt())
                .build();
    }

    public Page<AdminResDTO> toAdminResponseDTOList(Page<Admin> admins) {
        return admins.map(this::toAdminResponseDTO);
    }
}
