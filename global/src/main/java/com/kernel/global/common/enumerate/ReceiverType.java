package com.kernel.global.common.enumerate;

public enum ReceiverType {

    CUSTOMER("수요자"),
    MANAGER("매니저"),
    ADMIN("관리자");

    private final String label;

    ReceiverType(String label) {
        this.label = label;
    }
}
