package com.kernel.reservation.service;


import com.kernel.reservation.service.request.ReservationReqDTO;
import com.kernel.reservation.service.response.common.MatchedManagersRspDTO;
import com.kernel.sharedDomain.domain.entity.Reservation;

import java.util.List;

public interface MatchService {

    // 매칭 매니저 조회
    List<MatchedManagersRspDTO> getMatchingManagers(ReservationReqDTO reservationReqDTO, Long customerId);

    // 매칭 상태 변경
    void changeStatus(Long reservationId);

    // 예약 매칭 저장
    void saveReservationMatch(Long userId, Reservation foundReservation, Long selectedManagerId);
}
