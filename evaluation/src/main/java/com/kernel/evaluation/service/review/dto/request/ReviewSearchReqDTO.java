package com.kernel.evaluation.service.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Schema(description = "리뷰 검색 요청 DTO")
@ToString
public class ReviewSearchReqDTO {

    @Schema(description = "리뷰 평점", example = "5")
    private Integer rating;
}