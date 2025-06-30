package com.kernel.evaluation.service.review.dto.response;

import com.kernel.evaluation.domain.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "매니저 리뷰 응답 DTO")
public class ManagerReviewRspDTO {

    @Schema(description = "리뷰 ID", example = "1001")
    private Long reviewId;

    @Schema(description = "예약 ID", example = "2002")
    private Long reservationId;

    @Schema(description = "리뷰 평점", example = "5", minimum = "1", maximum = "5")
    private Integer rating;

    @Schema(description = "리뷰 내용", example = "서비스가 매우 만족스러웠습니다.")
    private String content;

    @Schema(description = "리뷰 작성 일시", example = "2023-01-01T14:00:00")
    private LocalDateTime createdAt;

    @Schema(hidden = true)
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