package com.kernel.reservation.port;

import com.kernel.reservation.common.enums.ReservationErrorCode;
import com.kernel.reservation.common.exception.ReservationException;
import com.kernel.reservation.repository.common.ReservationQueryPortRepository;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.service.ReservationQueryPort;
import com.kernel.sharedDomain.service.response.ReservationManagerMappingInfo;
import com.kernel.sharedDomain.service.response.ReservationScheduleInfo;
import com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationQueryPortImpl implements ReservationQueryPort {

    private final ReservationQueryPortRepository portRepository;

    /**
     * 예약 일정 및 매칭 조회
     * @param reservationId 예약ID
     * @param userId 수요자ID
     * @return ScheduleAndMatchInfo
     */
    @Override
    public ScheduleAndMatchInfo findScheduleAndMatchByReservationIdAndUserId(Long reservationId, Long userId) {

        return portRepository.findScheduleAndMatchByReservationIdAndUserId(reservationId, userId)
                .orElseThrow(() -> new  ReservationException(ReservationErrorCode.NOT_FOUND_RESERVATION));
    }

    /**
     * 예약 조회
     * @param reservationId 예약ID
     * @param userId 수요자ID
     * @return Reservation
     */
    @Override
    public Reservation findReservationByReservationIdAndUserId(Long reservationId, Long userId) {

        return portRepository.findByReservationIdAndUser_UserId(reservationId, userId)
                .orElseThrow(() -> new  ReservationException(ReservationErrorCode.NOT_FOUND_RESERVATION));
    }

    /**
     * 예약 일정 및 매칭 조회 List
     * @param userId 수요자ID
     * @param reservationIds 조회할 예약 ID List
     * @return ScheduleAndMatchInfo
     */
    @Override
    public List<ScheduleAndMatchInfo> findSchedulesAndMatchesByUserIdAndReservationIds(Long userId, List<Long> reservationIds) {

        return portRepository.findSchedulesAndMatchesByUserIdAndReservationIds(reservationIds, userId);
    }

    /**
     * 예약 테이블과 예약 매칭 테이블을 조인해서 매니저 id와 reservationId로 예약 조회
     * @param reservationId 예약ID
     * @param managerId 매니저ID
     * @return Reservation
     */
    @Override
    public Reservation findReservationByReservationIdAndManagerId(Long reservationId, Long managerId) {
        return portRepository.findReservationByReservationIdAndManagerId(reservationId, managerId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND_RESERVATION_MATCH));
    }

    /**
     * 주급 정산 가능한 예약 조회
     * @param start 시작 날짜
     * @param end 끝 날짜
     * @return List<Reservation>
     */
    @Override
    public List<Reservation> getCompletedReservationsWithoutSettlement(LocalDate start, LocalDate end) {
        return portRepository.findCompletedReservationsWithoutSettlement(start, end);
    }

    /**
     * 예약 ID 기반 매칭 매니저 조회
     * @param reservationIds 시작 날짜
     * @return List<ReservationManagerMappingInfo>
     */
    @Override
    public List<ReservationManagerMappingInfo> getManagerIdsByReservationIds(List<Long> reservationIds) {
        return portRepository.findManagerIdsByReservationIds(reservationIds);
    }

    /**
     * 예약 일정 및 매칭 조회 List
     * @param reservationIds 조회할 예약 ID List
     * @return ReservationScheduleInfo
     */
    @Override
    public List<ReservationScheduleInfo> getReservationSchedule(List<Long> reservationIds) {
        return portRepository.getReservationSchedule(reservationIds);
    }

    /**
     * 이번주 예상 정산 금액 조회 (예약확정, 방문완료)
     * @param userId 매니저ID
     * @param startOfWeek 시작 날짜
     * @param endOfWeek 종료 날짜
     * @return ThisWeekEstimatedInfo
     */
    @Override
    public Long getThisWeekEstimated(Long userId, LocalDate startOfWeek, LocalDate endOfWeek) {
        return portRepository.getThisWeekEstimated(userId, startOfWeek, endOfWeek);
    }

    /**
     * 기간별 정산 금액 조회
     * @param userId 매니저ID
     * @param start 시작 날짜
     * @param end 종료 날짜
     * @return ThisWeekEstimatedInfo
     */
    @Override
    public Long getSettledAmount(Long userId, LocalDate start, LocalDate end) {
        return portRepository.getSettledAmount(userId, start, end);
    }

    /**
     * 날짜, 매니저Id 기반 방문완료 예약 조회
     * @param userId 매니저ID
     * @param start 시작 날짜
     * @param end 종료 날짜
     * @return ThisWeekEstimatedInfo
     */
    @Override
    public List<Long> getCompletedReservationIdByScheduleAndManagerId(Long userId, LocalDate start, LocalDate end) {
        return portRepository.getCompletedReservationIdByScheduleAndManagerId(userId, start, end);
    }

    /**
     * 관리자 정산 조회
     * @param startDate 검색 시작 날짜
     * @param endDate 검색 종료 날짜
     * @return 조회된 예약 및 매니저 정보
     */
    @Override
    public List<ScheduleAndMatchInfo> getReservationForSettlementByAdmin(LocalDate startDate, LocalDate endDate) {
        return portRepository.getReservationForSettlementByAdmin(startDate, endDate);
    }

    /**
     * 관리자 이번주 예상 금액 조회
     * @param startDate 검색 시작 날짜
     * @param endDate 검색 종료 날짜
     * @return 이번주 예상 금액 조회
     */
    @Override
    public Long getThisWeekEstimatedForAdmin(LocalDate startDate, LocalDate endDate) {
        return portRepository.getThisWeekEstimatedForAdmin(startDate, endDate);
    }

    /**
     * 관리자 날짜기반 정산 금액 조회
     * @param startDate 검색 시작 날짜
     * @param endDate 검색 종료 날짜
     * @return 조회된 정산 금액
     */
    @Override
    public List<Long> getSettledAmountWithoutUserId(LocalDate startDate, LocalDate endDate) {
        return portRepository.getSettledAmountWithoutUserId(startDate, endDate);
    }
}
