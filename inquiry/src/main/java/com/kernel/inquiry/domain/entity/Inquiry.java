package com.kernel.inquiry.domain.entity;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.common.enums.CustomerInquiryCategory;
import com.kernel.inquiry.common.enums.ManagerInquiryCategory;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "inquiry")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Inquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long inquiryId;

    // 카테고리 조회용
    @Column(name = "category_name")
    private Enum<?> category;

    // 작성자 ID
    @Column
    @CreatedBy
    private Long authorId;

    // 작성자 유형
    @Column
    private UserRole authorRole;

    // 제목
    @Column(length = 50)
    private String title;

    // 내용
    @Column(length = 5000)
    private String content;

    // 첨부파일 ID
    @Column
    private Long fileId;

    // 답변 여부
    @Column
    @Builder.Default
    private Boolean isReplied = Boolean.FALSE;

    // 삭제 여부
    @Column
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    public void delete() {
        this.isDeleted = true;
    }

    public void update(Inquiry request) {
        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }
        if (request.getContent() != null) {
            this.content = request.getContent();
        }
        if (request.getFileId() != null) {
            this.fileId = request.getFileId();
        }
        if (request.getCategory() != null) {
            this.category = request.getCategory();
        }
    }
}
