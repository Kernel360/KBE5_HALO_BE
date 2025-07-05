package com.kernel.sharedDomain.service;

import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo;

import java.util.List;

public interface ReservationQueryPort {

    // 예약 일정 및 매칭 조회 by reservationId, userId
    ScheduleAndMatchInfo findScheduleAndMatchByReservationIdAndUserId(Long reservationId, Long userId);

    // 예약 조회 by reservationId, userId
    Reservation findReservationByReservationIdAndUserId(Long reservationId, Long userId);

    // 예약 일정 및 매칭 조회 List by userId, status
    List<ScheduleAndMatchInfo> findSchedulesAndMatchesByUserIdAndStatus(Long userId, ReservationStatus status);
}
