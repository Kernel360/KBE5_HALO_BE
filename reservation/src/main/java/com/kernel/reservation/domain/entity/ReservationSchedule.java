package com.kernel.reservation.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.sharedDomain.domain.entity.Reservation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation_schedule")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationSchedule extends BaseEntity {

    // 예약ID
    @Id
    private Long reservationId;

    // 예약 FK
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // PK이자 FK
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    // 요청 날짜
    @Column(nullable = false)
    private LocalDate requestDate;

    // 시작 시간
    @Column(columnDefinition = "time(0)", nullable = false)
    private LocalTime startTime;

    // 소요 시간
    @Column(nullable = false)
    private Integer turnaround;

    @PreRemove
    private void preventRemove() {
        throw new UnsupportedOperationException("예약 관련 정보는 삭제할 수 없습니다.");
    }
}
