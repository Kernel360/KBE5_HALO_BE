package com.kernel.reservation.domain.enums;

public enum MatchStatus {
    PENDING("대기"),
    MATCHED("매니저 수용"),
    REJECTED("매니저 불수용"),
    RESERVATION_CANCELED("예약 취소");

    private final String label;

    MatchStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
