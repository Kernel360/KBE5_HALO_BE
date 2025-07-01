package com.kernel.admin.domain.entity;

import com.kernel.admin.domain.enums.BoardType;

public class Board {
    private Long noticeId;
    private BoardType boardType;
    private String title;
    private String content;
    private Long file_Id;
    private Boolean is_Deleted;
    private Long views;
}