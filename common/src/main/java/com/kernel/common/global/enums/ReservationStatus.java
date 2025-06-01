package com.kernel.common.global.enums;

public enum ReservationStatus {

    REQUESTED("예약 요청"),             // 요청됨
    CONFIRMED("예약 확정"),             // 확정됨
    IN_PROGRESS("예약 진행 중"),         // 진행 중
    COMPLETED("예약 완료"),             // 완료됨
    CANCELED("예약 취소"),              // 취소됨
    REJECTED("예약 거절"),              // 거절됨
    REFUND_PROCESSING("환불 진행중"),    // 환불 진행 중
    REFUND_COMPLETED("환불 완료"),      // 환불 완료
    REFUND_REJECTED("환불 거절");       // 환불 거절

    private final String label;

    ReservationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
