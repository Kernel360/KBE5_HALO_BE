package com.kernel.common.reservation.entity;

import com.kernel.common.customer.entity.Customer;
import com.kernel.common.global.entity.BaseEntity;
import com.kernel.common.global.entity.ServiceCategory;
import com.kernel.common.global.enums.ReservationStatus;
import com.kernel.common.manager.entity.Manager;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "reservation")
@Getter
@SuperBuilder
@NoArgsConstructor
public class Reservation extends BaseEntity {

    // 예약 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    // 서비스 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceCategory serviceCategory;

    // 수요자 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // 매니저 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    // 우편번호
    // TODO: 구글맵API 사용 시, 필요한 컬럼만 정리 필요
    @Column(length = 10, nullable = false)
    private String zipcode;

    // 도로명 주소
    // TODO: 구글맵API 사용 시, 필요한 컬럼만 정리 필요
    @Column(length = 200, nullable = false)
    private String roadAddress;

    // 상세 주소
    // TODO: 구글맵API 사용 시, 필요한 컬럼만 정리 필요
    @Column(length = 100, nullable = false)
    private String detailAddress;

    // 청소 요청 날짜
    @Column(nullable = false)
    private LocalDate requestDate;

    // 청소 시작 희망 시간
    @Column(nullable = false)
    private LocalTime startTime;

    // 소요 시간
    @Column(nullable = false)
    @Builder.Default
    private Integer turnaround = 0;

    // 예약 상태
    // TODO: 수요자가 예약을 완료하면 기본 상태값 뭐로 할지 확인 필요. 임시로 예약 요청으로 해둠
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.REQUESTED;

    // 예약 금액
    @Column(nullable = false)
    @Builder.Default
    private Integer price = 0;

    // 고객 요청사항 메모
    @Column(length = 5000)
    private String memo;

    // 예약 취소 이유
    @Column
    private String cancelReason;
}
