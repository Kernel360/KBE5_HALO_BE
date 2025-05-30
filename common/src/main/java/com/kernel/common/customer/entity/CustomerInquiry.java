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

    // 카테고리ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private InquiryCategory category;

    // 게시글ID
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
    @Column(nullable = false, columnDefinition = "Boolean DEFAULT false")
    private Boolean isDeleted;

    // 게시글 답변
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id", referencedColumnName = "inquiry_id", insertable = false, updatable = false)
    private CustomerReply customerReply;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        //this.fileId = fileId; TODO 첨부파일 추가
    }

    public void delete() {
        this.isDeleted = true;
    }

}
