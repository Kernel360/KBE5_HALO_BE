package com.kernel.admin.common.enums;

public enum BoardType {
    NOTICE("공지"),
    EVENT("이벤트");

    private String BoardName;

    BoardType(String boardName) {
        this.BoardName = boardName;
    }

    public String getBoardName() {
        return BoardName;
    }
}
