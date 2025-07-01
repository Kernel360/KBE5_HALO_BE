package com.kernel.member.service.response;

import com.kernel.global.common.enums.UserStatus;
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
    private String phone;
    private String email;
    private BigDecimal averageRating;
    private UserStatus userstatus;
    private Integer reservationCount;
    private Integer reviewCount;

    // AdminManagerSummaryRspDTO ->  AdminManagerSummaryRspDTO 리스트
    public static Page<AdminManagerSummaryRspDTO> toDTOList(Page<ManagerSummaryInfo> managers) {
        return managers.map(manager -> AdminManagerSummaryRspDTO.builder()
                .managerId(manager.getManagerId())
                .userName(manager.getUserName())
                .phone(manager.getPhone())
                .email(manager.getEmail())
                .averageRating(manager.getAverageRating())
                .userstatus(manager.getUserstatus())
                .reservationCount(manager.getReservationCount())
                .reviewCount(manager.getReviewCount())
                .build());
    }

}
