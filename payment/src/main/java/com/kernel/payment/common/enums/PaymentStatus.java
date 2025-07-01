package com.kernel.payment.common.enums;

public enum PaymentStatus {

    SUCCESS("결제 성공"),
    FAILED("결제 실패"),
    CANCELLED("결제 취소"),
    REFUNDED("환불");

    private String label;
    PaymentStatus(String label) {this.label = label;}
}
