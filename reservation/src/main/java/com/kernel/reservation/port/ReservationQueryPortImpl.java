package com.kernel.reservation.port;

import com.kernel.reservation.common.enums.ReservationErrorCode;
import com.kernel.reservation.common.exception.ReservationException;
import com.kernel.reservation.repository.common.ReservationQueryPortRepository;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.service.ReservationQueryPort;
import com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
     * @param status 예약 상태
     * @return ScheduleAndMatchInfo
     */
    @Override
    public List<ScheduleAndMatchInfo> findSchedulesAndMatchesByUserIdAndStatus(Long userId, ReservationStatus status) {

        return portRepository.findSchedulesAndMatchesByUserIdAndStatus(status, userId);
    }

}
