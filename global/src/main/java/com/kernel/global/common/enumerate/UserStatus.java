package com.kernel.global.common.enumerate;

public enum UserStatus {

    ACTIVE("활성"),                    // 활성
    SUSPENDED("정지"),                 // 정지
    DELETED("탈퇴");                   // 탈퇴

    private final String label;

    UserStatus(String label) {
        this.label = label;
    }
}
