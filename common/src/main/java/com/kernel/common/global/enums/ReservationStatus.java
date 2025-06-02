package com.kernel.common.global.enums;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    REQUESTED("예약 요청"),
    CONFIRMED("예약 확정"),
    IN_PROGRESS("예약 진행 중"),
    COMPLETED("예약 완료"),
    CANCELED("예약 취소"),
    REJECTED("예약 거절"),
    REFUND_PROCESSING("환불 진행중"),
    REFUND_COMPLETED("환불 완료"),
    REFUND_REJECTED("환불 거절");

    private final String label;

    ReservationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
