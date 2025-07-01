package com.kernel.reservation.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReservationErrorCode {

    // 포인트 부족
    INSUFFICIENT_POINTS(400,"RESERVATION001","포인트가 부족합니다.");

    private final int status;
    private final String code;
    private final String message;
}
