package com.kernel.common.enums;

import lombok.Getter;

@Getter
public enum ReplyStatus {
    ANSWERED("답변완료"),
    PENDING("대기중");

    private final String label;

    ReplyStatus(String label) {
        this.label = label;
    }
}