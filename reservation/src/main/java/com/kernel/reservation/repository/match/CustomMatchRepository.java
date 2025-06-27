package com.kernel.reservation.repository.match;


import com.kernel.reservation.service.response.common.ReservationRspDTO;
import org.apache.catalina.Manager;

import java.util.List;

public interface CustomMatchRepository {

    // 매칭 매니저 조회
    List<Manager> getMatchedManagers(ReservationRspDTO reservationReqDTO);

    // 매칭 매니저 조회
    List<ManagerMatchingRspDTO> getMatchingManagersInfo(Long customerId, List<Long> matchedManagerIds);
}
