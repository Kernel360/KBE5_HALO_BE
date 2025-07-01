package com.kernel.evaluation.service.review.dto.response;

import com.kernel.evaluation.domain.info.CustomerReviewInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Schema(description = "고객 리뷰 응답 DTO")
public class CustomerReviewRspDTO {

    @Schema(description = "리뷰 ID", example = "1001")
    private Long reviewId;

    @Schema(description = "예약 ID", example = "2002")
    private Long reservationId;

    @Schema(description = "서비스 요청 날짜", example = "2023-01-01")
    private LocalDate requestDate;

    @Schema(description = "서비스 요청 시간", example = "14:00")
    private LocalTime startTime;

    @Schema(description = "서비스 소요 시간(분)", example = "120")
    private Integer turnaround;

    @Schema(description = "서비스 카테고리명", example = "청소 서비스")
    private String serviceCategoryName;

    @Schema(description = "리뷰 별점", example = "5", minimum = "1", maximum = "5")
    private Integer rating;

    @Schema(description = "리뷰 내용", example = "서비스가 매우 만족스러웠습니다.")
    private String content;

    @Schema(description = "리뷰 작성 일자", example = "2023-01-01T14:00:00")
    private LocalDateTime createdAt;

    @Schema(hidden = true)
    public static CustomerReviewRspDTO fromInfo(CustomerReviewInfo info) {
        return CustomerReviewRspDTO.builder()
                .reviewId(info.getReviewId())
                .reservationId(info.getReservationId())
                .requestDate(info.getRequestDate())
                .startTime(info.getStartTime())
                .turnaround(info.getTurnaround())
                .serviceCategoryName(info.getServiceCategoryName())
                .rating(info.getRating())
                .content(info.getContent())
                .createdAt(info.getCreatedAt())
                .build();
    }
}