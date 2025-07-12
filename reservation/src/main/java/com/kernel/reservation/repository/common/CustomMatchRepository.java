package com.kernel.reservation.repository.common;

import com.kernel.reservation.service.info.MatchedManagersInfo;
import com.kernel.reservation.service.request.ManagerReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomMatchRepository {

    // 매칭 매니저 조회
    List<Long> getMatchedManagers(ManagerReqDTO managerReqDTO);

    // 매칭 매니저 정보 조회
    Page<MatchedManagersInfo> getMatchingManagersInfo(Long userId, List<Long> matchedManagers, Pageable pageable);
}
