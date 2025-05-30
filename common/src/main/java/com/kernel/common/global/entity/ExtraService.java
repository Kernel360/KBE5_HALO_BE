package com.kernel.common.global.entity;

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
    // 추가 서비스 예약 엔터티 정의

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long extraServiceId;

    // 추가 서비스도 서비스 카테코리에 정의되어 있어서 1:N 관계로 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceCategory serviceCategory;

    // TODO: Reservation 엔터티 생성 후 사용할 필드
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;*/

}
