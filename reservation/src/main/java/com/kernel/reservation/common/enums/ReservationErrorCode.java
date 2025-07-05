package com.kernel.reservation.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReservationErrorCode {

    INSUFFICIENT_POINTS(400,"RESERVATION-001","포인트가 부족합니다."),
    NO_AVAILABLE_MANAGER(404,"RESERVATION-002","요청하신 일정에 가능한 매니저가 없습니다."),
    NOT_FOUND_RESERVATION(404,"RESERVATION-003","예약이 존재하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;
}
