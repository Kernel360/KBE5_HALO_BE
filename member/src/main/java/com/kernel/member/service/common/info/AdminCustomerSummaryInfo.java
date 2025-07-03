package com.kernel.member.service.common.info;

import com.kernel.global.common.enums.UserStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminCustomerSummaryInfo {
    private Long userId;
    private String email;
    private String userName;
    private String phone;
    private UserStatus status;
    private Integer point;
}
