package com.kernel.admin.notice;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "notice")
@Data
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;
    private String title;

    @Lob
    private String content;
    private Long fileId;
    private Boolean isDeleted;
    private Timestamp createdAt;
    private Long createdBy;
    private Timestamp updatedAt;
    private Long updatedBy;
    private Long views;

    public void update(String title, String content, Long fileId, Long updatedBy) {
        this.title = title;
        this.content = content;
        this.fileId = fileId;
        this.updatedAt = new java.sql.Timestamp(System.currentTimeMillis());
        this.updatedBy = updatedBy;
    }

    // setter 안쓰는 방향으로 작성해봤는데 리뷰 부탁드립니다..
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }


}