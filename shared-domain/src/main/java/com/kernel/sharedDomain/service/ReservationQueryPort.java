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

    // 예약 테이블과 예약 메칭 테이블을 조인해서 매니저 id와 reservationId로 예약 조회
    Reservation findReservationByReservationIdAndManagerId(Long reservationId, Long managerId);
}
