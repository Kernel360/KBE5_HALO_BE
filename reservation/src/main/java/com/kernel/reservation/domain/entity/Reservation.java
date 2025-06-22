package com.kernel.reservation.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.User;
import com.kernel.reservation.domain.enumerate.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name="reservation")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Reservation extends BaseEntity {

    // 예약ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    // 수요자ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "user_id", nullable = false)
    private User user;

    // 예약 상태
    @Column(nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.REQUESTED;

    // 예약 금액
    @Column(nullable = false)
    private Integer price;

    // 고객 요청 사항
    @Column(nullable = false, length = 5000)
    private String memo;

    // 핸드폰 번호
    @Column(nullable = false)
    private String phone;

    // 서비스ID
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "service_id", nullable = false)
    private ServiceCategory serviceCategory;

}
