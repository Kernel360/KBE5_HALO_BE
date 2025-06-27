package com.kernel.reservation.service;


import com.kernel.reservation.service.response.MatchedManagersRspDTO;
import com.kernel.reservation.service.response.common.ReservationRspDTO;

import java.util.List;

public interface MatchService {

    // 매칭 매니저 조회
    List<MatchedManagersRspDTO> getMatchingManagers(ReservationRspDTO reservationReqDTO, Long customerId);

    // 매칭 상태 변경
    void changeStatus(Long reservationId);
}
