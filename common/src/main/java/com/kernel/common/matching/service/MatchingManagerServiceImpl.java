package com.kernel.common.matching.service;

import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.entity.Manager;
import com.kernel.common.matching.dto.ManagerMatchingRspDTO;
import com.kernel.common.matching.dto.MatchingManagerMapper;
import com.kernel.common.matching.repository.MatchingManagerRepository;
import com.kernel.common.reservation.dto.request.CustomerReservationReqDTO;
import com.kernel.common.reservation.dto.request.ReservationConfirmReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingManagerServiceImpl implements MatchingManagerService {

    private final MatchingManagerRepository matchingRepository;
    private final MatchingManagerMapper matchingMapper;

    /**
     * 매니저 매칭 리스트 조회
     * @param reservationReqDTO 예약요청DTO
     * @return 매니저 매칭 리스트
     */
    @Override
    @Transactional
    public List<ManagerMatchingRspDTO> getMatchingManagers(CustomerReservationReqDTO reservationReqDTO) {
        // TODO 매칭 알고리즘 구현
        List<Manager> matchedManagers = matchingRepository.findByStatus(UserStatus.ACTIVE);

        // 매칭 매니저 상태값 변경
        matchedManagers.forEach(manager -> {
            manager.updateStatus(UserStatus.MATCHING);
        });

        return matchingMapper.toRspDTOList(matchedManagers);
    }

    /**
     * 예약 확정 후 매니저 상태 변경
     * @param confirmReqDTO 예약 확정 DTO
     */
    @Override
    @Transactional
    public void updateMatchingManagersStatus(ReservationConfirmReqDTO confirmReqDTO) {

        // 매칭 매니저리스트 조회
        List<Manager> matchedManagers = matchingRepository.findByManagerIdIn(confirmReqDTO.getMatchedManagerIds());

        // 매칭 매니저 상태값 변경
        matchedManagers.forEach(manager -> {
            manager.updateStatus(UserStatus.ACTIVE);
        });

    }
}
