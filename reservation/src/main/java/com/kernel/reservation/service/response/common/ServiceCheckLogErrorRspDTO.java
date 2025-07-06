package com.kernel.reservation.service.response.common;

import com.kernel.reservation.common.exception.ServiceCheckLogException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "서비스 체크 로그 에러 응답 DTO")
@Builder
public class ServiceCheckLogErrorRspDTO {

    @Schema(description = "요청 성공 여부", example = "false", required = true)
    private final boolean success;

    @Schema(description = "에러 코드", example = "400", required = true)
    private final String code;

    @Schema(description = "에러 메시지", example = "잘못된 요청입니다.", required = true)
    private final String message;

    @Schema(description = "타임스탬프", example = "2023-10-01T12:00:00", required = true)
    private final LocalDateTime timestamp;

    public static ServiceCheckLogErrorRspDTO createErrorResponse(ServiceCheckLogException errorCode) {
        return ServiceCheckLogErrorRspDTO.builder()
                .success(false)
                .code(errorCode.getErrorCode().getCode())
                .message(errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
