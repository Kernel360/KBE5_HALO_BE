package com.kernel.evaluation.service.review.dto.response;

import com.kernel.evaluation.domain.info.ManagerReviewInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "매니저 리뷰 페이지 응답 DTO")
public class ManagerReviewPageRspDTO {

    @Schema(description = "리뷰 ID", example = "1001")
    private Long reviewId;

    @Schema(description = "예약 ID", example = "2002")
    private Long reservationId;

    @Schema(description = "작성자 ID", example = "3003")
    private Long authorId;

    @Schema(description = "작성자명", example = "홍길동")
    private String authorName;

    @Schema(description = "리뷰 평점", example = "5", minimum = "1", maximum = "5")
    private Integer rating;

    @Schema(description = "리뷰 내용", example = "서비스가 매우 만족스러웠습니다.")
    private String content;

    @Schema(description = "서비스 카테고리 ID", example = "4004")
    private Long serviceId;

    @Schema(description = "서비스 카테고리명", example = "청소 서비스")
    private String serviceName;

    @Schema(description = "작성 일시", example = "2023-01-01T14:00:00")
    private LocalDateTime createdAt;

    @Schema(hidden = true)
    public static ManagerReviewPageRspDTO fromInfo(ManagerReviewInfo info) {
        return ManagerReviewPageRspDTO.builder()
                .reviewId(info.getReviewId())
                .reservationId(info.getReservationId())
                .authorId(info.getAuthorId())
                .authorName(info.getUserName())
                .rating(info.getRating())
                .content(info.getContent())
                .serviceId(info.getServiceId())
                .serviceName(info.getServiceName())
                .createdAt(info.getCreatedAt())
                .build();
    }
}