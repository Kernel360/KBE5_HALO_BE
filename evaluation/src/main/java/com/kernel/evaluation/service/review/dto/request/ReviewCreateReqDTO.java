package com.kernel.evaluation.service.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Schema(description = "리뷰 생성 요청 DTO")
public class ReviewCreateReqDTO {

    @NotNull(message = "리뷰 평점은 필수입니다.")
    @Min(1)
    @Max(5)
    @Schema(description = "리뷰 평점", example = "5", minimum = "1", maximum = "5")
    private Integer rating;

    @NotBlank(message = "리뷰 내용을 작성해주세요.")
    @Size(min = 5, max = 600, message = "리뷰는 최소 5글자, 최대 600글자까지 작성 가능합니다.")
    @Schema(description = "리뷰 내용", example = "서비스가 매우 만족스러웠습니다.", minLength = 5, maxLength = 600)
    private String content;

}