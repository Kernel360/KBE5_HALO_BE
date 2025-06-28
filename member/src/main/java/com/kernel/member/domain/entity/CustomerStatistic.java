package com.kernel.member.domain.entity;

import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "customer_statistic")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerStatistic  extends BaseEntity {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // PK이자 FK
    @JoinColumn(name = "user_id")
    private User user;

    // 평점
    @Column(nullable = false)
    @Builder.Default
    private BigDecimal averageRating = BigDecimal.ZERO;

    // 리뷰 수
    @Column(nullable = false)
    @Builder.Default
    private Integer reviewCount = 0;

    // 예약 수
    @Column(nullable = false)
    @Builder.Default
    private Integer reservationCount = 0;

}
