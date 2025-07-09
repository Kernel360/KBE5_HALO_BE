package com.kernel.member.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberStatisticErrorCode {

    // 통계 관련
    CUSTOMER_STATISTIC_NOT_FOUND(404, "STATISTIC-001", "고객 통계 정보를 찾을 수 없습니다."),
    MANAGER_STATISTIC_NOT_FOUND(404, "STATISTIC-002", "매니저 통계 정보를 찾을 수 없습니다."),
    CONCURRENT_UPDATE_ERROR(409, "STATISTIC-003", "요청이 정상적으로 처리되지 않았습니다. 잠시 후 다시 시도해주세요.");

    private final int status;
    private final String code;
    private final String message;
}
