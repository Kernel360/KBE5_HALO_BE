package com.kernel.common.manager.entity;

import com.kernel.common.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "manager_inquiry")
@Getter
@SuperBuilder
@NoArgsConstructor
public class ManagerInquiry extends BaseEntity {

    // 매니저 게시글ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    // 작성자ID(= 매니저ID)
    @Column(nullable = false)
    private Long authorId;

    // 제목
    @Column(nullable = false, length = 50)
    private String title;

    // 내용
    @Column(nullable = false, length = 5000)
    private String content;

    // 첨부파일
    @Column
    private Long fileId;

    // 삭제여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    public void update(String title, String content, Long fileId) {
        this.title = title;
        this.content = content;
        this.fileId = fileId;
    }

    @OneToOne(mappedBy = "managerInquiry", fetch = FetchType.LAZY)
    private ManagerReply managerReply;

    public void delete() {
        this.isDeleted = true;
    }
}
