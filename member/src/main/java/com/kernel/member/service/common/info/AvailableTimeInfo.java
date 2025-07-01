package com.kernel.member.service.common.info;

import com.kernel.member.common.enums.DayOfWeek;
import com.kernel.member.domain.entity.AvailableTime;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class AvailableTimeInfo {

    // 업무 가능 요일
    private DayOfWeek dayOfWeek;

    // 업무 시작 시간 (HH:mm 형식)
    private LocalTime time;

    // List of available times from entity
    public static List<AvailableTimeInfo> fromEntityList(List<AvailableTime> availableTimes) {
        return availableTimes.stream()
                .map(availableTime -> AvailableTimeInfo.builder()
                        .dayOfWeek(availableTime.getDayOfWeek())
                        .time(availableTime.getTime())
                        .build())
                .toList();
    }
}
