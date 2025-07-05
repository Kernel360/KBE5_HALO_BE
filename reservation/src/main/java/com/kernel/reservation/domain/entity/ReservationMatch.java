package com.kernel.reservation.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.User;
import com.kernel.sharedDomain.common.enums.MatchStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;
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

    //예약 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 매니저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    // 확정 날짜 (매니저 수용/불수용)
    @Column(nullable = true)
    private LocalDate confirmAt;

    // 매칭 상태
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MatchStatus status = MatchStatus.PENDING;

    @PreRemove
    private void preventRemove() {
        throw new UnsupportedOperationException("예약 관련 정보는 삭제할 수 없습니다.");
    }

    public void changeStatus(MatchStatus status) {
        this.status = status;
    }

}
