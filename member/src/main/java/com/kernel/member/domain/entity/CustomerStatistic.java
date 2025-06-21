package com.kernel.member.domain.entity;

import com.kernel.global.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "customer_statistic")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerStatistic {

    @Id
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // PK이자 FK
    @JoinColumn(name = "user_id")
    private User user;

    // 평점
    @Column(nullable = false)
    private BigDecimal rating;

    // 리뷰 수
    @Column(nullable = false)
    private Integer review_count;

    // 예약 수
    @Column(nullable = false)
    private Integer reservation_count;

}
