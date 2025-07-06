package com.kernel.inquiry.common.enums;

import com.kernel.global.common.enums.UserRole;

public enum AuthorType {
    MANAGER("매니저"),
    CUSTOMER("수요자");

    private final String label;

    AuthorType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static AuthorType fromUserRole(UserRole userRole) {
        return switch (userRole){
            case CUSTOMER -> AuthorType.CUSTOMER;
            case MANAGER -> AuthorType.MANAGER;
            default -> throw new IllegalArgumentException("지원하지 않는 사용자 권한입니다.");
        };
    }
}
