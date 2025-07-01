package com.kernel.evaluation.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 작성자 유형")
public enum ReviewAuthorType {

    @Schema(description = "수요자")
    CUSTOMER("수요자"),

    @Schema(description = "매니저")
    MANAGER("매니저");

    private final String label;

    ReviewAuthorType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}