package com.kernel.evaluation.service.review.dto.response;

import com.kernel.evaluation.domain.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ManagerReviewRspDTO {

    // 리뷰ID
    private Long reviewId;

    // 예약ID
    private Long reservationId;

    // 리뷰 평점
    private Integer rating;

    // 리뷰 내용
    private String content;

    // 리뷰 작성일시
    private LocalDateTime createdAt;

    public static ManagerReviewRspDTO fromEntity(Review review) {
        return ManagerReviewRspDTO.builder()
                .reviewId(review.getReviewId())
                .reservationId(review.getReservation().getReservationId())
                .rating(review.getRating())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
