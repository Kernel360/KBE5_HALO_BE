package com.kernel.common.global.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

    ACTIVE("활성"),                    // 활성
    SUSPENDED("정지"),                 // 정지
    DELETED("탈퇴"),                   // 탈퇴
    PENDING("승인대기"),                // 매니저 승인 대기
    REJECTED("승인거절"),               // 매니저 승인 거절
    TERMINATION_PENDING("계약해지대기"), // 매니저 계약 해지 대기
    TERMINATED("계약해지");             // 매니저 계약 해지

    private final String label;

    UserStatus(String label) {
        this.label = label;
    }
}
