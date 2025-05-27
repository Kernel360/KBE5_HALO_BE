package com.kernel.app.enums;

import lombok.Getter;

@Getter
public enum UserType {

    CUSTOMER("수요자"), MANAGER("매니저"), ADMIN("관리자");

    private final String label;

    UserType(String label) {
        this.label = label;
    }

}
