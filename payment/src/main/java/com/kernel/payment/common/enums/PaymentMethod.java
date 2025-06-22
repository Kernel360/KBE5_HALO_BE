package com.kernel.payment.common.enums;


public enum PaymentMethod {

    CARD("카드"),
    CASH("현금"),
    POINT("포인트");

    private final String label;

    PaymentMethod(String label){ this.label = label; }
}
