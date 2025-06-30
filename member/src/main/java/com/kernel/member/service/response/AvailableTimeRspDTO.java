package com.kernel.member.service.response;

import java.time.LocalTime;

import com.kernel.member.common.enums.DayOfWeek;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "가능 시간 응답 DTO")
public class AvailableTimeRspDTO {

    @Schema(description = "가능 시간 ID", example = "1", required = true)
    private Long timeId;

    @Schema(description = "가능 요일", example = "MONDAY", required = true)
    private DayOfWeek dayOfWeek;

    @Schema(description = "가능 시간", example = "14:00", required = true)
    private LocalTime time;
}