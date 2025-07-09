package com.kernel.member.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberStatisticErrorCode {

    // 통계 관련
    CUSTOMER_STATISTIC_NOT_FOUND(404, "STATISTIC-001", "고객 통계 정보를 찾을 수 없습니다."),
    MANAGER_STATISTIC_NOT_FOUND(404, "STATISTIC-002", "매니저 통계 정보를 찾을 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
