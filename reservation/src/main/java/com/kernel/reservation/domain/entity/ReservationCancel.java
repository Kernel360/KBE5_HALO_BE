package com.kernel.reservation.domain.entity;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.sharedDomain.domain.entity.Reservation;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_cancel")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class ReservationCancel extends BaseEntity {

    // 예약 ID
    @Id
    private Long reservationId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 예약 취소자
    @Column(nullable = false)
    private Long canceledById;

    // 예약 취소자 타입
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole canceledByType;

    // 예약 취소 날짜
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime cancelDate = LocalDateTime.now();

    // 예약 취소 사유
    @Column(nullable = false, length = 50)
    private String cancelReason;

    @PreRemove
    private void preventRemove() {
        throw new UnsupportedOperationException("예약 관련 정보는 삭제할 수 없습니다.");
    }

}


