package com.kernel.common.global.enums;

public enum NoticeType {
    NOTICE("공지"),
    EVENT("이벤트");

    private String noticeName;

    NoticeType(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeName() {
        return noticeName;
    }
}