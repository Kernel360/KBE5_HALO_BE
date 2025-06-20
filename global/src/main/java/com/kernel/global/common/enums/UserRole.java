package com.kernel.global.common.enums;

public enum UserRole {
    CUSTOMER("수요자"),
    MANAGER("매니저"),
    ADMIN("관리자");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

}
