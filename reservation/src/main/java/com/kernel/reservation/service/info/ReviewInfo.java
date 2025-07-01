package com.kernel.reservation.service.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInfo {

    // 리뷰ID
    private Long reviewId;

    // 리뷰 내용
    private String content;

    // 리뷰 별점
    private Integer rating;

    // 리뷰 작성일자
    private LocalDateTime createdAt;
}
