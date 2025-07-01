package com.kernel.member.service.response;

import com.kernel.common.global.enums.UserStatus;

import lombok.*;

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

}
