package com.kernel.inquiry.common.enums;

public enum AuthorType {
    MANAGER("매니저"), // 매니저
    CUSTOMER("고객"); // 고객

    private final String label;

    AuthorType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
