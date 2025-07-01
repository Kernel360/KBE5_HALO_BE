package com.kernel.evaluation.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "피드백 타입")
public enum FeedbackType {

    @Schema(description = "좋아요")
    LIKE("좋아요"),

    @Schema(description = "아쉬워요")
    DISLIKE("아쉬워요");

    private final String label;

    FeedbackType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}