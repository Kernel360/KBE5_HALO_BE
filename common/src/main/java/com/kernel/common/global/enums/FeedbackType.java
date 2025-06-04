package com.kernel.common.global.enums;

import lombok.Getter;

@Getter
public enum FeedbackType {

    LIKE("좋아요"),
    DISLIKE("아쉬워요");

    private final String label;

    FeedbackType(String label) {this.label = label;}
}
