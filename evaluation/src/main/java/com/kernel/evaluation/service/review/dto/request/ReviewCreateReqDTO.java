package com.kernel.evaluation.service.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ReviewCreateReqDTO {

    // 리뷰 평점
    @NotNull(message = "리뷰 평점은 필수입니다.")
    @Min(1)
    @Max(5)
    private Integer rating;

    // 리뷰 내용
    @NotBlank(message = "리뷰 내용을 작성해주세요.")
    @Size(min = 5, max = 600, message = "리뷰는 최소 5글자, 최대 600글자까지 작성 가능합니다.")
    private String content;

}
