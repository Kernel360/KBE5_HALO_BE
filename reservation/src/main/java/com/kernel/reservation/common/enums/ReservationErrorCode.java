package com.kernel.reservation.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReservationErrorCode {

    // 포인트 부족
    INSUFFICIENT_POINTS(400,"RESERVATION-001","포인트가 부족합니다."),
    
    // 매니저 없음
    NO_AVAILABLE_MANAGER(404,"RESERVATION-002","요청하신 일정에 가능한 매니저가 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
