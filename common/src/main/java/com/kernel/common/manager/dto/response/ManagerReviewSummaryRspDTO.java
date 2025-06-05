package com.kernel.common.manager.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerReviewSummaryRspDTO {

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

    // QueryDSL Projections.constructor(...)에서 사용할 생성자
    public ManagerReviewSummaryRspDTO(
        Long reviewId,
        Long reservationId,
        Long authorId,
        String authorName,
        Integer rating,
        String content,
        Long serviceId,
        String serviceName,
        LocalDateTime createdAt
    ) {
        this.reviewId = reviewId;
        this.reservationId = reservationId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.rating = rating;
        this.content = content;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.createdAt = createdAt;
    }
}
