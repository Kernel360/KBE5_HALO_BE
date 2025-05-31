package com.kernel.common.manager.dto.request;

import com.kernel.common.global.enums.DayOfWeek;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class AvailableTimeReqDTO {

    // 가능 요일
    @NotNull(message = "요일은 필수입니다.")
    private DayOfWeek dayOfWeek;

    // 가능 시간
    @NotNull(message = "가능 시간은 필수입니다.")
    private LocalTime time;
}
