package com.kernel.reservation.service.response.common;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReviewRspDTO {

    // 리뷰ID
    private Long reviewId;

    // 리뷰 내용
    private String reviewContent;

    // 리뷰 별점
    private Integer reviewRating;

    // 리뷰 작성일자
    private LocalDateTime reviewDate;
}
