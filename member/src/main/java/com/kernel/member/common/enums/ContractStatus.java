package com.kernel.member.common.enums;

public enum ContractStatus {

    PENDING("승인대기"),                // 매니저 승인 대기
    APPROVED("승인완료"),              // 매니저 승인 완료
    REJECTED("승인거절"),               // 매니저 승인 거절
    TERMINATION_PENDING("계약해지대기"), // 매니저 계약 해지 대기
    TERMINATED("계약해지");             // 매니저 계약 해지

    private final String label;

    ContractStatus(String label) {
        this.label = label;
    }
}

