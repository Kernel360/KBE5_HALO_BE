package com.kernel.reservation.service.response.common;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class StatisticRspDTO {

    // userID
    private Long userId;

    // 리뷰 수
    private Integer reviewCount;

    // 예약 수
    private Integer reservationCount;

    // 별점 평균
    private BigDecimal averageRating;
    /*
    public static StatisticRspDTO fromEntity() {
        return StatisticRspDTO.builder()
                .userId()
                .reviewCount()
                .reservationCount()
                .averageRating()
                .build();
    }
     */
}
