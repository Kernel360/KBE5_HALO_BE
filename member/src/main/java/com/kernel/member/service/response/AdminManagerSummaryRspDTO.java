package com.kernel.member.service.response;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.common.enums.ContractStatus;
import com.kernel.member.service.common.info.ManagerSummaryInfo;

import lombok.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminManagerSummaryRspDTO {

    private Long managerId;
    private String userName;
    private String bio;
    private String phone;
    private String email;
    private BigDecimal averageRating;
    private UserStatus userstatus;
    private ContractStatus contractStatus;
    private Integer reservationCount;
    private Integer reviewCount;
    private String profileImagePath; // 프로필 이미지 경로

    // AdminManagerSummaryRspDTO ->  AdminManagerSummaryRspDTO 리스트
    public static Page<AdminManagerSummaryRspDTO> toDTOList(Page<ManagerSummaryInfo> managers) {
        return managers.map(manager -> AdminManagerSummaryRspDTO.builder()
                .managerId(manager.getUserId())
                .userName(manager.getUserName())
                .bio(manager.getBio())
                .phone(manager.getPhone())
                .email(manager.getEmail())
                .averageRating(manager.getAverageRating())
                .userstatus(manager.getUserstatus())
                .contractStatus(manager.getContractStatus())
                .reservationCount(manager.getReservationCount())
                .reviewCount(manager.getReviewCount())
                .profileImagePath(manager.getFilePathsJson() != null ? manager.getFilePathsJson() : null)
                .build());
    }

}
