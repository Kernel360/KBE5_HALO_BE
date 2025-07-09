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

    // 고객의 예약수 업데이트
    // count를 받는 이유는 예약상태가 "COMPLETED"로 바뀌었을 때 1추가하고 환불처리되었을 때 -1을 하기 위함
    public void updateReservationCount(Integer count) {
        this.reservationCount += count;
    }

}
