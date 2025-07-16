package com.kernel.settlement.common.exception;

import com.kernel.global.common.enums.ErrorCodeInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SettlementErrorCode implements ErrorCodeInterface {

    NO_MATCHING_MANAGER(400, "MATCHING-001", "매니저가 매칭되지 않은 예약입니다.");

    private final int status;
    private final String code;
    private final String message;
}
