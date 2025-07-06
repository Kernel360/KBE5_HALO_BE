package com.kernel.inquiry.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.common.enums.CustomerInquiryCategory;
import com.kernel.inquiry.common.enums.InquiryCategory;
import com.kernel.inquiry.common.enums.ManagerInquiryCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    // 카테고리
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    // 작성자 ID
    @Column(nullable = false)
    private Long authorId;

    // 작성자 유형
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorType authorType;

    // 제목
    @Column(length = 50, nullable = false)
    private String title;

    // 내용
    @Column(length = 5000, nullable = false)
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

    @Transient
    public InquiryCategory getCategoryEnum() {
        return switch (authorType) {
            case CUSTOMER -> CustomerInquiryCategory.valueOf(categoryName);
            case MANAGER -> ManagerInquiryCategory.valueOf(categoryName);
            default -> throw new IllegalStateException("지원되지 않는 역할입니다.");
        };
    }

    public void delete() {
        this.isDeleted = true;
    }

    public void update(String updateTitle, String updateContent, Long updateFileId, String updateCategory) {

        if (updateTitle != null && !title.equals(updateTitle)) {
            this.title = updateTitle;
        }
        if (updateContent != null && !content.equals(updateContent)) {
            this.content = updateContent;
        }
        if (updateFileId != null && !fileId.equals(updateFileId)) {
            this.fileId = updateFileId;
        }
        if (updateCategory != null && !categoryName.equals(updateCategory)) {
            this.categoryName = updateCategory;
        }
    }

    public void replied(){
        this.isReplied = Boolean.TRUE;
    }
}
