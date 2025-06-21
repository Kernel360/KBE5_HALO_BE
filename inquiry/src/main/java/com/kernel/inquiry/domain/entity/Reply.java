package com.kernel.inquiry.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "reply")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reply extends BaseEntity {

    // 답변 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long replyId;

    // 문의사항 ID
    @OneToOne
    @JoinColumn
    private Inquiry inquiryId;

    // 작성자 ID
    @Column
    private Long authorId;

    // 내용
    @Column(length = 5000)
    private String content;

    // 첨부파일 ID;
    @Column
    private Long fileId;

}
