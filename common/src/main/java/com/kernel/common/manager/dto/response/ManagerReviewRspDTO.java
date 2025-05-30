package com.kernel.common.manager.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerReviewRspDTO {

    // 리뷰ID
    private Long reviewId;

    // 예약ID
    private Long reservationId;

    // 작성자ID(=매니저ID)
    private Long authorId;

    // 리뷰 평점
    private Integer rating;

    // 리뷰 내용
    private String content;
}
