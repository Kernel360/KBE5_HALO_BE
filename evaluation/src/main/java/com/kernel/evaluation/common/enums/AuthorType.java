package com.kernel.evaluation.common.enums;

public enum AuthorType {

    CUSTOMER("수요자"),
    MANAGER("매니저");

    private final String label;

    AuthorType(String label) {this.label = label;}
}
