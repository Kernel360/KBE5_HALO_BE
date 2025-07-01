package com.kernel.reservation.service.response.common;

import com.kernel.reservation.service.info.CustomerReservationDetailInfo;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class StatisticRspDTO {

    // 리뷰 수
    private Integer reviewCount;

    // 예약 수
    private Integer reservationCount;

    // 별점 평균
    private BigDecimal averageRating;

    // CustomerReservationDetailInfo -> StatisticRspDTO
    public static StatisticRspDTO fromInfo(CustomerReservationDetailInfo info) {
        return StatisticRspDTO.builder()
                .reviewCount(info.getReviewCount())
                .reservationCount(info.getReservationCount())
                .averageRating(info.getAverageRating())
                .build();
    }

}
