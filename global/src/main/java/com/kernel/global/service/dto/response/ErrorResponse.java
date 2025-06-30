package com.kernel.global.service.dto.response;

import com.kernel.global.common.enums.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "에러 응답 DTO")
public class ErrorResponse {

    @Schema(description = "요청 성공 여부", example = "false", required = true)
    private final boolean success;

    @Schema(description = "에러 코드", example = "400", required = true)
    private final String code;

    @Schema(description = "에러 메시지", example = "잘못된 요청입니다.", required = true)
    private final String message;

    @Schema(description = "타임스탬프", example = "2023-10-01T12:00:00", required = true)
    private final LocalDateTime timestamp;

    public ErrorResponse(ErrorCode errorCode) {
        this.success = false;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}