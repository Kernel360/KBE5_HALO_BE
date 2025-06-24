package com.kernel.evaluation.domain.entity;


import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "feedback",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_FEEDBACK_CUSTOMER_MANAGER",
                        columnNames = {"customer_id","manager_id"}
                )
        })@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseEntity {

    // 피드백 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    // 작성자 (수요자)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    // 대상자 (매니저)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    // 피드백 타입
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedbackType type;

    // 삭제 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    // 피드백 타입 변경
    public void changeType(FeedbackType type) {
        this.type = type;
    }

    // 피드백 상태 변경
    public void changeDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
