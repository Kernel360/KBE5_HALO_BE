package com.kernel.evaluation.service.review.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ManagerReviewSearchCondDTO {

    // 작성일시 시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromCreatedAt;

    // 작성일시 종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toCreatedAt;

    // 리뷰 평점
    private Integer ratingOption;

    // 고객명키워드
    private String customerNameKeyword;

    // 내용키워드
    private String contentKeyword;

    public LocalDateTime getFromDateTime() {
        // fromCreatedAt - LocalDate → LocalDateTime 보정
        return fromCreatedAt != null ? fromCreatedAt.atStartOfDay() : null;
    }

    public LocalDateTime getToDateTime() {
        // toCreatedAt - LocalDate → LocalDateTime 보정
        return toCreatedAt != null ? toCreatedAt.atTime(23, 59, 59) : null;
    }
}
