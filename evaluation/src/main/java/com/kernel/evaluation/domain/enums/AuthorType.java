package com.kernel.evaluation.domain.enums;

import lombok.Getter;

@Getter
public enum AuthorType {
    CUSTOMER("수요자"),
    MANAGER("매니저");

    private final String label;

    AuthorType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
