package com.kernel.member.service.request;

import com.kernel.member.common.enums.DayOfWeek;
import com.kernel.member.domain.entity.AvailableTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
@Schema(description = "가능 시간 요청 DTO")
public class AvailableTimeReqDTO {

    @Schema(description = "가능 요일", example = "MONDAY", required = true)
    @NotNull(message = "요일은 필수입니다.")
    private DayOfWeek dayOfWeek;

    @Schema(description = "가능 시간", example = "14:00", required = true)
    @NotNull(message = "업무 가능 시간은 필수입니다.")
    private LocalTime time;

    public static AvailableTime toEntity(AvailableTimeReqDTO reqDTO) {
        return AvailableTime.builder()
                .dayOfWeek(reqDTO.getDayOfWeek())
                .time(reqDTO.getTime())
                .build();
    }
}