package com.kernel.common.matching.repository;

import com.kernel.common.manager.entity.Manager;
import com.kernel.common.matching.dto.ManagerMatchingRspDTO;
import com.kernel.common.reservation.dto.response.ReservationRspDTO;

import java.util.List;

public interface CustomMatchingRepository {

    // 매칭 매니저 조회getMatchedManagers
    List<Manager> getMatchedManagers(ReservationRspDTO reservationReqDTO);

    // 매칭 매니저 조회
    List<ManagerMatchingRspDTO> getMatchingManagersInfo(Long customerId, List<Long> matchedManagerIds);
}
