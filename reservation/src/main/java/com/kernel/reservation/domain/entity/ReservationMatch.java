package com.kernel.reservation.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.reservation.domain.enums.MatchStatus;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.config.YamlProcessor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_match")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class ReservationMatch extends BaseEntity {

    //매칭 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long matchId;

    //예약 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 매니저 ID
    @Column(name = "manager_id", nullable = false)
    private Long managerId;

    // 매칭 일시
    @Column(nullable = false)
    private LocalDateTime matchedAt;

    // 확정 일시
    @Column(nullable = false)
    private LocalDateTime confirmedAt;

    // 매칭 상태
    @Column(nullable = false)
    private MatchStatus matchStatus;

    @PreRemove
    private void preventRemove() {
        throw new UnsupportedOperationException("예약 관련 정보는 삭제할 수 없습니다.");
    }
}
