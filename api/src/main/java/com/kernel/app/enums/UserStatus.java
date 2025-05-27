package com.kernel.app.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

    ACTIVE("활성"),
    REJECTED("승인거절"),      // 매니저 승인거절
    SUSPENDED("정지"),
    DELETED("탈퇴"),
    PENDING("승인대기") ;       // 매니저 승인대기중

    private final String label;

    UserStatus(String label) {
        this.label = label;
    }
}
