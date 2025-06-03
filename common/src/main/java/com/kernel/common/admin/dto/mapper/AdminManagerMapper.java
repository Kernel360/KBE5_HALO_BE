package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.response.AdminManagerSummaryRspDTO;
import com.kernel.common.admin.dto.response.AdminManagerRspDTO;
import com.kernel.common.manager.entity.Manager;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdminManagerMapper {

    // Manager Entity -> AdminManagerRspDTO
    public AdminManagerRspDTO toAdminManagerRspDTO(Manager manager) {
        return AdminManagerRspDTO.builder()
                .managerId(manager.getManagerId())
                .userName(manager.getUserName())
                .email(manager.getEmail())
                .phone(manager.getPhone())
                .status(manager.getStatus())
                .createdAt(manager.getCreatedAt())
                .updatedAt(manager.getUpdatedAt())
                .reservationCount(manager.getReservationCount())
                .bio(manager.getBio())
                .profileImageId(manager.getProfileImageId())
                //.availableArea(manager.getAvailableArea())    // availableArea는 Manager 패키지에서 정의된 Entity라고 가정
                //.availableTime(manager.getAvailableTime())    // availableTime는 Manager 패키지에서 정의된 Entity라고 가정
                .build();
    }

    // Manager Entity -> AdminManagerSummeryDTO
    public AdminManagerSummaryRspDTO toAdminSummeryResponseDTO(Manager manager) {
        return AdminManagerSummaryRspDTO.builder()
                .managerId(manager.getManagerId())
                .userName(manager.getUserName())
                .userstatus(manager.getStatus())
                .reservationCount(manager.getReservationCount())
                .reviewCount(manager.getReviewCount())
                .build();
    }

    // AdminManagerSummaryRspDTO ->  AdminManagerSummaryRspDTO 리스트
    public Page<AdminManagerSummaryRspDTO> toAdminManagerSummaryDTOList(Page<Manager> managers) {
        return managers.map(this::toAdminSummeryResponseDTO);
    }
}
