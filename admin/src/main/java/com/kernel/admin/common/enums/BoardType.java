package com.kernel.admin.common.enums;

public enum BoardType {
    NOTICE("공지"),
    EVENT("이벤트");

    private String noticeName;

    BoardType(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeName() {
        return noticeName;
    }
}