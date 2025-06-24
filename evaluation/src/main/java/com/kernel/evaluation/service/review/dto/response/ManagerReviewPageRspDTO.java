package com.kernel.evaluation.service.review.dto.response;

import com.kernel.evaluation.domain.info.ManagerReviewInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ManagerReviewPageRspDTO {

    // 리뷰ID
    private Long reviewId;

    // 예약ID
    private Long reservationId;

    // 작성자ID
    private Long authorId;

    // 작성자명
    private String authorName;

    // 리뷰 평점
    private Integer rating;

    // 리뷰 내용
    private String content;

    // 서비스 카테고리
    private Long serviceId;

    // 서비스 카테고리명
    private String serviceName;

    // 작성 일시
    private LocalDateTime createdAt;

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
