package com.kernel.common.matching.service;

import com.kernel.common.matching.dto.ManagerMatchingRspDTO;
import com.kernel.common.reservation.dto.request.CustomerReservationReqDTO;
import com.kernel.common.reservation.dto.request.ReservationConfirmReqDTO;

import java.util.List;

public interface MatchingManagerService {

    // 매칭 매니저 조회
    List<ManagerMatchingRspDTO> getMatchingManagers(CustomerReservationReqDTO reservationReqDTO);

    // 매칭 매니저 리스트 상태 변경
    void updateMatchingManagersStatus(ReservationConfirmReqDTO confirmReqDTO);
}
