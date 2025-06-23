package com.kernel.member.service.response;

import java.time.LocalTime;

import com.kernel.member.common.enums.DayOfWeek;
import lombok.Getter;

@Getter
public class AvailableTimeRspDTO {

    // 가능시간ID
    private Long timeId;

    // 가능 요일
    private DayOfWeek dayOfWeek;

    // 가능 시간
    private LocalTime time;
}
