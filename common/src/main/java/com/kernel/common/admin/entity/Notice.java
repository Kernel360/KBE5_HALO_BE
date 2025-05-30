package com.kernel.common.admin.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "notice")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private Long fileId;
    private Boolean Deleted;
    private Long views;

    public void update(String title, String content, Long fileId, Long updatedBy) {
        this.title = title;
        this.content = content;
        this.fileId = fileId;
    }

    public void setDeleted(Boolean Deleted) {
        this.Deleted = Deleted;
    }
}