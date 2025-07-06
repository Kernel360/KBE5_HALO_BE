package com.kernel.evaluation.service.review.dto.common.response;

import com.kernel.evaluation.common.exception.ReviewException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "리뷰 에러 응답 DTO")
@Getter
@Builder
public class ReviewErrorRspDTO {

    @Schema(description = "요청 성공 여부", example = "false", required = true)
    private final boolean success;

    @Schema(description = "에러 코드", example = "400", required = true)
    private final String code;

    @Schema(description = "에러 메시지", example = "잘못된 요청입니다.", required = true)
    private final String message;

    @Schema(description = "타임스탬프", example = "2023-10-01T12:00:00", required = true)
    private final LocalDateTime timestamp;

    public static ReviewErrorRspDTO createErrorResponse(ReviewException e) {
        return ReviewErrorRspDTO.builder()
                .success(false)
                .code(e.getErrorCode().getCode())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
