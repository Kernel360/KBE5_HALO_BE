package com.kernel.common.customer.entity;

import com.kernel.common.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "customer_reply")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomerReply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    // 문의사항ID
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private CustomerInquiry customerInquiry;

    // 작성자ID
    @Column(nullable = false)
    private Long authorId;

    // 내용
    @Column(nullable = false, length = 5000)
    private String content;

    // 첨부파일 ID;
    @Column
    private Long fileId;

    /**
     * 문의사항 답변 수정
     *
     * @param content 답변 내용
     * @param fileId  첨부파일 ID
     */
    public void update(String content, Long fileId) {
        this.content = content;
        this.fileId = fileId;
    }
}