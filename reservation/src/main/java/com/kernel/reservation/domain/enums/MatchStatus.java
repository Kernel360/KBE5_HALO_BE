package com.kernel.reservation.domain.enums;

public enum MatchStatus {
    PENDING("대기중"),
    MATCHED("매칭됨"),
    REJECTED("거절됨");

    private final String label;

    MatchStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
