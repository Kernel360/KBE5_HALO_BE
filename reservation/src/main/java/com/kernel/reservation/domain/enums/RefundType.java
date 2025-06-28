package com.kernel.reservation.domain.enums;

public enum RefundType {
    REFUND_PROCESSING("환불 진행중"),
    REFUND_COMPLETED("환불 완료"),
    REFUND_REJECTED("환불 거절");

    private final String label;

    RefundType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
