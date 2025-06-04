package com.kernel.common.global.entity;

import com.kernel.common.customer.entity.Customer;
import com.kernel.common.global.enums.FeedbackType;
import com.kernel.common.manager.entity.Manager;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "feedback",
       uniqueConstraints = {
           @UniqueConstraint(
                   name = "UQ_FEEDBACK_MANAGER_CUSTOMER",
                   columnNames = {"manager_id", "customer_id"}
           )
       })
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    // 매니저ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    // 수요자ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

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

    // 피드백 활성화
    public void deleted(boolean deleted) {
        this.deleted = deleted;
    }
}
