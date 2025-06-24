package com.kernel.evaluation.domain.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ManagerReviewInfo {

    // 리뷰ID
    private Long reviewId;

    // 예약ID
    private Long reservationId;

    // 작성자ID
    private Long authorId;

    // 작성자명
    private String userName;

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

}
