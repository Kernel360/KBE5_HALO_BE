package com.kernel.reservation.service;


import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import com.kernel.reservation.common.enums.MatchStatus;
import com.kernel.reservation.domain.entity.ReservationMatch;
import com.kernel.reservation.repository.CustomerReservationRepository;
import com.kernel.reservation.repository.common.MatchRepository;
import com.kernel.reservation.repository.common.ReservationUserRepository;
import com.kernel.reservation.service.info.MatchedManagersInfo;
import com.kernel.reservation.service.request.ChooseManagerReqDTO;
import com.kernel.reservation.service.request.ReservationReqDTO;
import com.kernel.reservation.service.response.common.MatchedManagersRspDTO;
import com.kernel.sharedDomain.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchingRepository;
    private final CustomerReservationRepository customerReservationRepository;
    private final ReservationUserRepository userRepository;

    /**
     * 매니저 매칭 리스트 조회
     * @param reservationReqDTO 예약요청DTO
     * @param userId 수요자Id
     * @return 매니저 매칭 리스트
     */
    @Override
    @Transactional
    public List<MatchedManagersRspDTO> getMatchingManagers(ReservationReqDTO reservationReqDTO, Long userId) {

        List<Long> matchedManagers = matchingRepository.getMatchedManagers(reservationReqDTO);

        List<MatchedManagersInfo> matchedManagersInfo = matchingRepository.getMatchingManagersInfo(userId, matchedManagers);

        return MatchedManagersRspDTO.fromInfoList(matchedManagersInfo);
    }

    /**
     * 예약 취소시 매칭 상태 변경
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

    /**
     * 예약 매칭 저장
     * @param userId 로그인한 유저
     * @param reservationId 예약ID
     * @param reqDTO 예약 매칭 매니저 ID
     * @return 확정한 예약 정보
     */
    @Override
    @Transactional
    public void saveReservationMatch(Long userId, Long reservationId, ChooseManagerReqDTO reqDTO) {

        // 1. 예약 조회
        Reservation foundReservation = customerReservationRepository.findByReservationIdAndUser_UserId(reservationId, userId)
                .orElseThrow(() -> new NoSuchElementException("예약이 존재하지 않습니다."));

        // 2. 매니저 조회
        User foundUser = userRepository.findByUserIdAndStatusAndRole(reqDTO.getSelectedManagerId(), UserStatus.ACTIVE, UserRole.MANAGER)
                .orElseThrow(() -> new NoSuchElementException("해당 매니저가 존재하지 않습니다."));

        // 3. 매칭 정보 저장
        matchingRepository.save(
                ReservationMatch.builder()
                    .reservation(foundReservation)
                    .manager(foundUser)
                    .build()
        );
    }
}
