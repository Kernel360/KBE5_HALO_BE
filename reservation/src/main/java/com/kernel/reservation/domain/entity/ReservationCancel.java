package com.kernel.reservation.domain.entity;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

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
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 예약 취소자
    @Column(nullable = false)
    private Long canceledById;

    // 예약 취소자 타입
    @Column(nullable = false)
    private UserRole canceledByType;

    // 예약 취소 날짜
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime cancelDate;

    // 예약 취소 사유
    @Column(nullable = false, length = 50)
    private String cancelReason;

    @PreRemove
    private void preventRemove() {
        throw new UnsupportedOperationException("예약 관련 정보는 삭제할 수 없습니다.");
    }

}


