package com.kernel.common.global.entity;

import com.kernel.common.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "extra_services")
@Getter
@NoArgsConstructor
@SuperBuilder
@ToString
// @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // application.yml에서 전역 설정했으므로 주석처리
public class ExtraService extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extraServiceId;

    // 예약 정보
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;


    // 추가 서비스(서비스 카테고리)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceCategory serviceCategory;
}
