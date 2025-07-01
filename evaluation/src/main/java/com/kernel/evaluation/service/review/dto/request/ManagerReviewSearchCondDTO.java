package com.kernel.evaluation.service.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "매니저 리뷰 검색 조건 DTO")
public class ManagerReviewSearchCondDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "작성일시 시작일", example = "2023-01-01")
    private LocalDate fromCreatedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "작성일시 종료일", example = "2023-12-31")
    private LocalDate toCreatedAt;

    @Schema(description = "리뷰 평점", example = "5")
    private Integer ratingOption;

    @Schema(description = "고객명 키워드", example = "홍길동")
    private String customerNameKeyword;

    @Schema(description = "내용 키워드", example = "서비스 만족")
    private String contentKeyword;

    @Schema(hidden = true)
    public LocalDateTime getFromDateTime() {
        return fromCreatedAt != null ? fromCreatedAt.atStartOfDay() : null;
    }

    @Schema(hidden = true)
    public LocalDateTime getToDateTime() {
        return toCreatedAt != null ? toCreatedAt.atTime(23, 59, 59) : null;
    }
}