package com.kernel.common.manager.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ManagerReviewReqDTO {

    // 리뷰 평점
    @NotNull(message = "리뷰 평점은 필수입니다.")
    @Min(1)
    @Max(5)
    private Integer rating;

    // 리뷰 내용
    @NotNull(message = "리뷰 내용은 필수입니다.")
    @Size(max = 600, message="리뷰 내용은 최대 600자까지 가능합니다.")
    private String content;
}
