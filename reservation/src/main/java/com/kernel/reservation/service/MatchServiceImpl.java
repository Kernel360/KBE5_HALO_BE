package com.kernel.reservation.service;


import com.kernel.global.domain.entity.User;
import com.kernel.reservation.common.enums.MatchStatus;
import com.kernel.reservation.domain.entity.ReservationMatch;
import com.kernel.reservation.repository.match.MatchRepository;
import com.kernel.reservation.service.response.MatchedManagersRspDTO;
import com.kernel.reservation.service.response.common.ReservationRspDTO;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Manager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchingRepository;

    /**
     * 매니저 매칭 리스트 조회
     * @param reservationReqDTO 예약요청DTO
     * @return 매니저 매칭 리스트
     */
    @Override
    @Transactional
    public List<MatchedManagersRspDTO> getMatchingManagers(ReservationRspDTO reservationReqDTO, Long customerId) {

        // TODO user로 매핑 후 추후 매니저로 변경
        List<User> matchedManagers = matchingRepository.getMatchedManagers(reservationReqDTO);

        List<Long> matchedManagerIds = matchedManagers.stream()
            .map(Manager::getManagerId)
            .toList();

        List<MatchedManagersRspDTO> matchedManagersInfo = matchingRepository.getMatchingManagersInfo(customerId, matchedManagerIds);

        return matchedManagersInfo;
    }

    /**
     * 예약 확정 후 매니저 상태 변경
     * @param reservationId 예약 ID
     */
    @Override
    @Transactional
    public void changeStatus(Long reservationId) {

        // 매칭 조회
        ReservationMatch foundMatch = matchingRepository.findByReservation_ReservationId(reservationId);

        // 매칭 상태값 변경
        foundMatch.changeStatus(MatchStatus.RESERVATION_CANCELED);

    }
}
