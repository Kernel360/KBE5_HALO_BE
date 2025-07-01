package com.kernel.member.service.request;

import com.kernel.member.common.enums.DayOfWeek;
import com.kernel.member.domain.entity.AvailableTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
@Schema(description = "가능 시간 수정 요청 DTO")
public class AvailableTimeUpdateReqDTO {

    @Schema(description = "가능 요일", example = "MONDAY", required = false)
    private DayOfWeek dayOfWeek;

    @Schema(description = "가능 시간", example = "14:00", required = false)
    private LocalTime time;

    @AssertTrue(message = "요일이 선택된 경우, 업무 시간은 필수입니다.")
    @Schema(description = "요일이 선택된 경우, 업무 시간이 필수인지 확인", required = true)
    private boolean isTimeRequired() {
        return dayOfWeek != null ? time != null : true;
    }

    public static AvailableTime toEntity(AvailableTimeUpdateReqDTO availableTimeUpdateReqDTO) {
        if (availableTimeUpdateReqDTO == null) {
            return null;
        }
        return AvailableTime.builder()
                .dayOfWeek(availableTimeUpdateReqDTO.getDayOfWeek())
                .time(availableTimeUpdateReqDTO.getTime())
                .build();
    }
}