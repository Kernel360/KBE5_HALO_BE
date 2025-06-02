package com.kernel.common.customer.entity;

import com.kernel.common.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "customer_inquiry")
@Getter
@SuperBuilder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerInquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    // 카테고리 조회용
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private InquiryCategory category;

    // 문의사항ID
    @Column(nullable = false)
    private Long authorId;

    // 제목
    @Column(nullable = false, length = 50)
    private String title;

    // 내용
    @Column(nullable = false, length = 5000)
    private String content;

    //TODO 첨부파일 추가
    /*
    * @Column(nullable = true)
    * private Long fileId;
    */

    // 삭제 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    // 문의사항 답변
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id", referencedColumnName = "inquiry_id", insertable = false, updatable = false)
    private CustomerReply customerReply;

    // 문의사항 수정
    public void update(String title, String content, InquiryCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
        //this.fileId = fileId; TODO 첨부파일 추가
    }

    // 문의사항 삭제
    public void delete() {
        this.isDeleted = true;
    }

    // 삭제 여부 확인
    public void validateDelete(){
        if(this.isDeleted)
            throw new IllegalStateException("이미 삭제된 문의사항 입니다.");
    }

    // 답변 여부 확인
    public void validateReply(){
        if(this.customerReply != null)
            throw new IllegalStateException("답변이 달린 문의사항은 삭제/수정할 수 없습니다.");
    }

}
