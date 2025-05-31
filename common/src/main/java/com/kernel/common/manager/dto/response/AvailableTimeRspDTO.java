package com.kernel.common.manager.dto.response;

import com.kernel.common.global.enums.DayOfWeek;
import java.time.LocalTime;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class AvailableTimeRspDTO {

    // 가능시간ID
    private Long timeId;

    // 가능 요일
    private DayOfWeek dayOfWeek;

    // 가능 시간
    private LocalTime time;
}
