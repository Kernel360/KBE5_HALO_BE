package com.kernel.reservation.repository.common;

import com.kernel.reservation.service.info.MatchedManagersInfo;
import com.kernel.reservation.service.request.ReservationReqDTO;
import com.kernel.reservation.service.response.common.MatchedManagersRspDTO;

import java.util.List;

public interface CustomMatchRepository {

    // 매칭 매니저 조회
    List<Long> getMatchedManagers(ReservationReqDTO reservationReqDTO);

    // 매칭 매니저 정보 조회
    List<MatchedManagersInfo> getMatchingManagersInfo(Long userId, List<Long> matchedManagers);
}
