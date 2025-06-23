package com.kernel.evaluation.domain.enums;

public enum FeedbackType {

    LIKE("좋아요"),
    DISLIKE("아쉬워요");

    private final String label;

    FeedbackType(String label) {this.label = label;}
}
