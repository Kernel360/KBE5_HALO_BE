package com.kernel.evaluation.domain.info;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CustomerReviewInfo {

    // 예약ID
    private Long reservationId;

    // 서비스 요청 날짜
    private LocalDate requestDate;

    // 서비스 요청 시간
    private LocalTime startTime;

    // 서비스 소요 시간
    private Integer turnaround;

    // 서비스 카테고리명
    private String serviceCategoryName;

    // 리뷰ID
    private Long reviewId;

    // 리뷰 별점
    private Integer rating;

    // 리뷰 내용
    private String content;

    // 리뷰 작성 일자
    private LocalDateTime createdAt;

}
