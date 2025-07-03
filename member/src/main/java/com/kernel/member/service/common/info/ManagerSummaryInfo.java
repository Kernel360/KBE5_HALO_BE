package com.kernel.member.service.common.info;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.common.enums.ContractStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ManagerSummaryInfo {
    private Long userId;
    private String userName;
    private String phone;
    private String email;
    private BigDecimal averageRating;
    private UserStatus userstatus;
    private ContractStatus contractStatus;
    private Integer reservationCount;
    private Integer reviewCount;
}
