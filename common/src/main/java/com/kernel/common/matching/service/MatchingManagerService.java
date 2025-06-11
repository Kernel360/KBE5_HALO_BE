package com.kernel.common.matching.service;

import com.kernel.common.matching.dto.ManagerMatchingRspDTO;
import com.kernel.common.reservation.dto.request.ReservationConfirmReqDTO;
import com.kernel.common.reservation.dto.response.ReservationRspDTO;

import java.util.List;

public interface MatchingManagerService {

    // 매칭 매니저 조회
    List<ManagerMatchingRspDTO> getMatchingManagers(ReservationRspDTO reservationReqDTO, Long customerId);

    // 매칭 매니저 리스트 상태 변경
    void updateMatchingManagersStatus(ReservationConfirmReqDTO confirmReqDTO);
}
