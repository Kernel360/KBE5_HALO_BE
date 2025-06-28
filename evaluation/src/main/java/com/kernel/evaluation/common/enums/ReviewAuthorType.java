package com.kernel.evaluation.common.enums;

public enum ReviewAuthorType {

    CUSTOMER("수요자"),
    MANAGER("매니저");

    private final String label;

    ReviewAuthorType(String label) {this.label = label;}
}
