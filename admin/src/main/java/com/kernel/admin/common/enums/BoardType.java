package com.kernel.admin.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시판 유형")
public enum BoardType {
    @Schema(description = "공지 게시판")
    NOTICE("공지"),

    @Schema(description = "이벤트 게시판")
    EVENT("이벤트");

    private String noticeName;

    BoardType(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeName() {
        return noticeName;
    }
}