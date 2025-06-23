package com.kernel.member.service.request;

import com.kernel.member.common.enums.DayOfWeek;

import com.kernel.member.domain.entity.AvailableTime;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AvailableTimeReqDTO {

    // 가능 요일
    @NotNull(message = "요일은 필수입니다.")
    private DayOfWeek dayOfWeek;

    // 가능 시간
    @NotNull(message = "가능 시간은 필수입니다.")
    private LocalTime time;

    // AvailableTimeReqDTO -> AvailableTime
    public static AvailableTime toEntity(AvailableTimeReqDTO reqDTO) {
        return AvailableTime.builder()
                .dayOfWeek(reqDTO.getDayOfWeek())
                .time(reqDTO.getTime())
                .build();
    }
}
