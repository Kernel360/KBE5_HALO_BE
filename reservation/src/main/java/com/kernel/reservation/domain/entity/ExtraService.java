package com.kernel.reservation.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "extra_service")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ExtraService {

    // 예약 추가 서비스 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long extraServiceId;

    // 예약 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 서비스 가격
    @Column(nullable = false)
    private Integer price;

    // 서비스 제공 시간
    @Column(nullable = false)
    private Integer serviceTime;

    @PreRemove
    private void preventRemove() {
        throw new UnsupportedOperationException("예약 관련 정보는 삭제할 수 없습니다.");
    }
}
