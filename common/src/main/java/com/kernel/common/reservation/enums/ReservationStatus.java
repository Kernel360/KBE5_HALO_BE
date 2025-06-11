package com.kernel.common.reservation.enums;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    PRE_CANCELED("예약 확정 전 취소"),
    REQUESTED("예약 요청"),
    IN_PROGRESS("서비스 진행 중"),
    CONFIRMED("예약 완료"),
    COMPLETED("방문 완료"),
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
