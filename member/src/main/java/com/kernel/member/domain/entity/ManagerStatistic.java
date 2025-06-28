package com.kernel.member.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "manager_statistic")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class ManagerStatistic extends BaseEntity {

    // 사용자 ID
    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    // 예약 수
    @Column(nullable = false)
    @Builder.Default
    private Integer reservationCount = 0;

    // 리뷰 수
    @Column(nullable = false)
    @Builder.Default
    private Integer reviewCount = 0;

    // 매니저가 받은 별점 평균
    @Column(precision = 2, scale = 1)
    @Builder.Default
    private BigDecimal averageRating = BigDecimal.ZERO;
}
