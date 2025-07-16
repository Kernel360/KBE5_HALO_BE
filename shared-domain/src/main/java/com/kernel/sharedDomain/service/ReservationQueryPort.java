package com.kernel.sharedDomain.service;

import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.service.response.ReservationManagerMappingInfo;
import com.kernel.sharedDomain.service.response.ReservationScheduleInfo;
import com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo;

import java.time.LocalDate;
import java.util.List;

public interface ReservationQueryPort {

    // 예약 일정 및 매칭 조회 by reservationId, userId
    ScheduleAndMatchInfo findScheduleAndMatchByReservationIdAndUserId(Long reservationId, Long userId);

    // 예약 조회 by reservationId, userId
    Reservation findReservationByReservationIdAndUserId(Long reservationId, Long userId);

    // 예약 일정 및 매칭 조회 List by userId, status
    List<ScheduleAndMatchInfo> findSchedulesAndMatchesByUserIdAndReservationIds(Long userId, List<Long> reservationIds);

    // 예약 테이블과 예약 매칭 테이블을 조인해서 매니저 id와 reservationId로 예약 조회
    Reservation findReservationByReservationIdAndManagerId(Long reservationId, Long managerId);

    // 정산을 위한 예약 조회
    List<Reservation> getCompletedReservationsWithoutSettlement(LocalDate start, LocalDate end);

    // 예약 ID 기반 매칭 매니저 ID 조회
    List<ReservationManagerMappingInfo> getManagerIdsByReservationIds(List<Long> reservationIds);

    // 예약 ID 기반 예약 일정 조회
    List<ReservationScheduleInfo> getReservationSchedule(List<Long> reservationIds);

    // 정산 예상 예약 조회 (예약확정, 방문완료)
    Long getThisWeekEstimated(Long userId, LocalDate startOfWeek, LocalDate endOfWeek);

    // 기간별 정산 금액 조회
    Long getSettledAmount(Long userId, LocalDate start, LocalDate end);

    // 날짜, 매니저Id 기반 방문완료 예약 조회
    List<Long> getCompletedReservationIdByScheduleAndManagerId(Long userId, LocalDate startDate, LocalDate startDate1);

    // 날짜 기반 정산된 예약 조회
    List<ScheduleAndMatchInfo> getReservationForSettlementByAdmin(LocalDate startDate, LocalDate endDate);

    // 날짜 기반 방문완료 예약 조회
    Long getThisWeekEstimatedForAdmin(LocalDate thisWeekStart, LocalDate thisWeekEnd);

    // 날짜 기반 정산금액 조회
    List<Long> getSettledAmountWithoutUserId(LocalDate start, LocalDate end);
}
