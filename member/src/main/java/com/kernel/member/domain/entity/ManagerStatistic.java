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

    // 동시성 제어를 위한 버전 필드
    @Version
    private Long version;

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
    @Column(nullable = false, precision = 3, scale = 2)
    @Builder.Default
    private BigDecimal averageRating = BigDecimal.ZERO;

    // 매니저의 예약수 업데이트
    // count를 받는 이유는 예약상태가 "COMPLETED"로 바뀌었을 때 1추가하고 환불처리되었을 때 -1을 하기 위함
    public void updateReservationCount(Integer count) {
        this.reservationCount += count;
    }

    // 매니저의 리뷰수 업데이트
    public void updateReviewCount() {
        this.reviewCount += 1;
    }

    // 매니저 평균 별점 업데이트
    public void updateAverageRating(BigDecimal updateRating) {
            averageRating = updateRating;
    }
}
