package com.kernel.reservation.domain.entity;

import com.kernel.sharedDomain.domain.entity.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "reservation_location")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ReservationLocation {
    // 예약 ID
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 위도
    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    // 경도
    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    // 주소
    @Column(nullable = false, length = 200)
    private String roadAddress;

    // 상세 주소
    @Column(length = 100)
    private String detailAddress;

    @PreRemove
    private void preventRemove() {
        throw new UnsupportedOperationException("예약 관련 정보는 삭제할 수 없습니다.");
    }
}
