package com.kernel.common.admin.dto.mapper;


import com.kernel.common.admin.dto.response.ManagerResponseDTO; // 추후에 ManagerResponseDTO가 정의된 위치로 변경 필요
import com.kernel.common.admin.entity.Manager;  // 추후에 Manager entity가 정의된 위치로 변경 필요
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component


public class ManagerMapper {

    // Entity -> ResponseDTO
    public ManagerResponseDTO toResponseDTO(Manager manager) {    // Manager는 manager 패키지에서 정의된 Entity 클래스라고 가정
        // TODO: Entity를 Response DTO로 변환하는 로직 구현
        return ManagerResponseDTO.builder()
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

    // Entity 리스트 -> ResponseDTO 리스트
    public List<ManagerResponseDTO> toResponseDTOList(List<Manager> managers) {   // managers는 manager 모듈에서 정의된 Entity 리스트라고 가정
        // TODO: List 조회에 필요한 정보만 추출하여 리스트로 변환하는 로직 구현
        return managers.stream()
                .map(manager -> {
                    ManagerResponseDTO dto = toResponseDTO(manager);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
