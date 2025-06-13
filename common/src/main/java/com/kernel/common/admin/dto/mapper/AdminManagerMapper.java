package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.response.AdminManagerSummaryRspDTO;
import com.kernel.common.admin.dto.response.AdminManagerRspDTO;
import com.kernel.common.manager.dto.response.AvailableTimeRspDTO;
import com.kernel.common.manager.entity.AvailableTime;
import com.kernel.common.manager.entity.Manager;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdminManagerMapper {

    // AvailableTime → AvailableTimeRspDTO
    private AvailableTimeRspDTO toAvailableTimeRspDTO(AvailableTime time) {
        return AvailableTimeRspDTO.builder()
                .dayOfWeek(time.getDayOfWeek()) // 가능 요일
                .time(time.getTime())           // 가능 시간
                .build();
    }

    // Manager Entity -> AdminManagerRspDTO
    public AdminManagerRspDTO toAdminManagerRspDTO(Manager manager) {
        return AdminManagerRspDTO.builder()
                .managerId(manager.getManagerId())
                .userName(manager.getUserName())
                .birthDate(manager.getBirthDate())
                .gender(manager.getGender())
                .email(manager.getEmail())
                .phone(manager.getPhone())
                .roadAddress(manager.getRoadAddress())
                .detailAddress(manager.getDetailAddress())
                .status(manager.getStatus())
                .averageRating(manager.getAverageRating())
                .reservationCount(manager.getReservationCount())
                .reviewCount(manager.getReviewCount())
                .bio(manager.getBio())
                .profileImageId(manager.getProfileImageId())
                .fileId(manager.getFileId())  // 첨부파일 ID
                .availableTimes(manager.getAvailableTimes().stream()
                        .map(this::toAvailableTimeRspDTO)
                        .toList())
                //.availableArea(manager.getAvailableArea())    // availableArea는 Manager 패키지에서 정의된 Entity라고 가정
                .createdAt(manager.getCreatedAt())
                .updatedAt(manager.getUpdatedAt())
                .contractAt(manager.getContractAt())
                .terminatedAt(manager.getTerminatedAt())
                .terminationReason(manager.getTerminationReason())
                .build();
    }

    // Manager Entity -> AdminManagerSummeryDTO
    public AdminManagerSummaryRspDTO toAdminSummeryResponseDTO(Manager manager) {
        return AdminManagerSummaryRspDTO.builder()
                .managerId(manager.getManagerId())
                .userName(manager.getUserName())
                .userstatus(manager.getStatus())
                .phone(manager.getPhone())
                .email(manager.getEmail())
                .averageRating(manager.getAverageRating())
                .reservationCount(manager.getReservationCount())
                .reviewCount(manager.getReviewCount())
                .build();
    }

    // AdminManagerSummaryRspDTO ->  AdminManagerSummaryRspDTO 리스트
    public Page<AdminManagerSummaryRspDTO> toAdminManagerSummaryDTOList(Page<Manager> managers) {
        return managers.map(this::toAdminSummeryResponseDTO);
    }
}
