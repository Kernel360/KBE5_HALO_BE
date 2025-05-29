package com.kernel.common.manager.entity;

import com.kernel.common.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name ="manager_reply")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerReply extends BaseEntity {

    // 매니저 게시글 답변ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(name = "inquiry_id", nullable = false, unique = true)
    private Long  inquiryId;

    // 작성자ID(= 관리자ID)
    @Column(nullable = false)
    private Long authorId;

    // 내용
    @Column(nullable = false, length = 5000)
    private String content;

    // TODO: 첨부파일 - 추후 작업 예정
    private Long fileId;
}
