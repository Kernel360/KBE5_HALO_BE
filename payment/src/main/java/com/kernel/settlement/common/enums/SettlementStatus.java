package com.kernel.settlement.common.enums;

public enum SettlementStatus {

    PENDING("정산 대기"),
    SETTLED("정산 완료"),
    CANCELED("정산 실패");

    private String label;
    SettlementStatus(String label) {this.label = label;}
}
