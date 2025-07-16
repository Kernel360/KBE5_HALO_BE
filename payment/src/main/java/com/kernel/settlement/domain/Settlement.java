package com.kernel.settlement.domain;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.User;
import com.kernel.settlement.common.enums.SettlementStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "settlement")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settlementId;

    // 매니저 Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    // 예약ID
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false, unique = true)
    private Reservation reservation;

    // 정산 금액
    @Column(nullable = false)
    private Integer totalAmount;

    // 수수료
    @Column(nullable = false)
    private Integer platformFee;

    // 정산 완료 일시
    @Column(nullable = true)
    private LocalDateTime settledAt;

    // 정산 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SettlementStatus status;

    // 정산자 ID
    // 0 일 경우, 스케줄러
    @Column(nullable = false)
    private Long settledBy;
}
