package com.kernel.common.admin.dto.response;

import com.kernel.common.global.enums.UserStatus;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminManagerSummaryRspDTO {

    private Long managerId;
    private String userName;
    private UserStatus userstatus;
    private Integer reservationCount;
    private Integer reviewCount;

}
