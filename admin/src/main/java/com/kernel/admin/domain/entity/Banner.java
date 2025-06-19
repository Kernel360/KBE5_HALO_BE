package com.kernel.admin.domain.entity;

import java.time.LocalDate;

public class Banner {
    private Long bannerId;
    private String title;
    private String path;
    private LocalDate startAt;
    private LocalDate endAt;
    private Long fileId;
    private Long views;
    private Boolean isDeleted;

}
