package com.kernel.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "manager_termination")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ManagerTermination {

    @Id
    private Long userId;

    // 매니저 ID
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private Manager manager;

    // 계약 해지 요청 일시
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime requestAt;

    // 계약 해지 사유
    @Column(nullable = false, length = 300) // 계약 해지 사유는 최대 300자
    private String reason;

    // 계약 해지 일시
    @Column
    private LocalDateTime terminatedAt;

    // 계약 해지 테이블 업데이트
    public void updateTerminatedAt() {
        this.terminatedAt = LocalDateTime.now();
    }
}
