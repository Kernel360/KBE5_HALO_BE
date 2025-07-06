package com.kernel.sharedDomain.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.User;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
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
    @JoinColumn(name = "user_id", nullable = false)
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
    @Column(nullable = true, length = 5000)
    private String memo;

    // 핸드폰 번호
    @Column(nullable = false)
    private String phone;

    // 서비스 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceCategory serviceCategory;

    @PreRemove
    private void preventRemove() {
        throw new UnsupportedOperationException("예약 관련 정보는 삭제할 수 없습니다.");
    }

    // 예약 상태 변경
    public void changeStatus(String reason, ReservationStatus reservationStatus) {
        this.status = reservationStatus;
        // TODO: 상태 변경 사유를 매개변수로 받는데 어떻게 처리할지 결정해야 함
    }

    // 매니저 예약 수락
    public void managerAccept() {
        this.status = ReservationStatus.CONFIRMED;
    }

    // 예약 체크인
    public void checkIn() {
        this.status = ReservationStatus.IN_PROGRESS;
    }

    // 예약 체크아웃
    public void checkOut() {
        this.status = ReservationStatus.COMPLETED;
    }

}
