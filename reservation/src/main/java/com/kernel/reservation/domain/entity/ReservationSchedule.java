package com.kernel.reservation.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_schedule")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ReservationSchedule {

    // 예약 ID
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservationId;

    // 요청 날짜
    @Column(nullable = false)
    private LocalDate requestDate;

    // 시작 시간
    @Column(nullable = false)
    private LocalDateTime startTime;

    // 소요 시간
    @Column(nullable = false)
    private Integer turnaround;

    @PreRemove
    private void preventRemove() {
        throw new UnsupportedOperationException("예약 관련 정보는 삭제할 수 없습니다.");
    }
}
