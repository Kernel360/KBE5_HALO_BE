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

    // 매니저 상담 게시글ID
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private ManagerInquiry managerInquiry;

    // 작성자ID(= 관리자ID)
    @Column(nullable = false)
    private Long authorId;

    // 내용
    @Column(nullable = false, length = 5000)
    private String content;

    // 첨부파일
    @Column
    private Long fileId;
}
