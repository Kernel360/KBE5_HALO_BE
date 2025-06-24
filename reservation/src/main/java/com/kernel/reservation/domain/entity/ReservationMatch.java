package com.kernel.reservation.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.User;
import com.kernel.reservation.common.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "reservation_match")
@SuperBuilder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationMatch extends BaseEntity {

    // 매치 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    // 매니저 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    // 예약 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 매치 날짜 (매니저-예약 연결)
    @Column(nullable = false)
    private LocalDate matchedAt;

    // 확정 날짜 (매니저 수용/불수용)
    @Column(nullable = false)
    private LocalDate confirmAt;

    // 매칭 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MatchStatus status = MatchStatus.PENDING;
}
