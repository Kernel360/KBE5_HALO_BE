package com.kernel.member.domain.entity;

import java.time.LocalTime;

public class AvailableTime {
    // 업무 가능 시간 ID
    private Long availableTimeId;

    // 매니저 ID
    private Long managerId;

    // 업무 가능 요일 (0: 일요일, 1: 월요일, ..., 6: 토요일)
    private Integer dayOfWeek;

    // 업무 시작 시간 (HH:mm 형식)
    private LocalTime time;
}
