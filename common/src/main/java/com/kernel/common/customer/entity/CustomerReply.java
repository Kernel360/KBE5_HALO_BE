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
    @Column(name = "inquiry_id", nullable = false, unique = true)
    private Long inquiryId;

    // 작성자ID
    @Column(nullable = false)
    private Long authorId;

    // 내용
    @Column(nullable = false)
    private String content;

    // TODO 첨부파일 추가
    // private Long fileId;
}