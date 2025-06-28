package com.kernel.reservation.common.enums;

public enum MatchStatus {
    PENDING("대기"),
    REJECTED("거절"),
    MATCHED("매치");

    private final String label;
    MatchStatus(String label) {this.label = label;}
}
