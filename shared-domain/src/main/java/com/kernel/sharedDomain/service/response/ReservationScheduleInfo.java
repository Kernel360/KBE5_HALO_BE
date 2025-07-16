package com.kernel.sharedDomain.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ReservationScheduleInfo {

    // 예약ID
    private Long reservationId;

    // 요청 날짜
    private LocalDate requestDate;

    // 시작 시간
    private LocalTime startTime;

    // 소요시간
    private Integer turnaround;

    // 서비스명
    private String serviceName;
}
