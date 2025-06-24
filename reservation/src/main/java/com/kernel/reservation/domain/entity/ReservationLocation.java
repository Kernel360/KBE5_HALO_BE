package com.kernel.reservation.domain.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private Reservation reservationId;

    // 위도
    @Column(nullable = false)
    private Double latitude;

    // 경도
    @Column(nullable = false)
    private Double longitude;

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
