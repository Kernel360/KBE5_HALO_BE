package com.kernel.evaluation.service.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Schema(description = "리뷰 업데이트 요청 DTO")
public class ReviewUpdateReqDTO {

    @NotNull(message = "리뷰를 작성할 예약을 선택해주세요.")
    @Schema(description = "예약 ID", example = "12345", required = true)
    private Long reservationId;

    @NotNull(message = "리뷰 평점은 필수입니다.")
    @Min(1)
    @Max(5)
    @Schema(description = "리뷰 평점", example = "4", minimum = "1", maximum = "5", required = true)
    private Integer rating;

    @NotBlank(message = "리뷰 내용을 작성해주세요.")
    @Size(min = 5, max = 600, message = "리뷰는 최소 5글자, 최대 600글자까지 작성 가능합니다.")
    @Schema(description = "리뷰 내용", example = "서비스가 만족스러웠습니다.", minLength = 5, maxLength = 600, required = true)
    private String content;
}