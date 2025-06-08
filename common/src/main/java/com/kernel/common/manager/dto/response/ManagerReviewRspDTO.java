package com.kernel.common.manager.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

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
}
