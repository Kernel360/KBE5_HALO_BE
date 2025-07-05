package com.kernel.reservation.service;

import com.kernel.global.common.enums.ErrorCode;
import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.common.exception.AuthException;
import com.kernel.global.domain.entity.User;
import com.kernel.global.repository.UserRepository;
import com.kernel.reservation.common.enums.ReservationErrorCode;
import com.kernel.reservation.common.exception.ReservationException;
import com.kernel.reservation.domain.entity.ReservationCancel;
import com.kernel.reservation.domain.entity.ReservationMatch;
import com.kernel.reservation.repository.ManagerReservationRepository;
import com.kernel.reservation.repository.ReservationMatchRepository;
import com.kernel.reservation.repository.common.ReservationCancelRepository;
import com.kernel.reservation.service.info.ManagerReservationDetailInfo;
import com.kernel.reservation.service.info.ManagerReservationSummaryInfo;
import com.kernel.reservation.service.request.ManagerReservationSearchCondDTO;
import com.kernel.reservation.service.request.ReservationCancelReqDTO;
import com.kernel.reservation.service.response.ManagerReservationRspDTO;
import com.kernel.reservation.service.response.ManagerReservationSummaryRspDTO;
import com.kernel.sharedDomain.common.enums.MatchStatus;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerReservationServiceImpl implements ManagerReservationService {

    private final UserRepository userRepository;
    private final ManagerReservationRepository managerReservationRepository;
    private final ReservationMatchRepository reservationMatchRepository;
    private final ReservationCancelRepository cancelRepository;

    /**
     * 매니저에게 할당된 예약 목록 조회 (검색 조건 및 페이징 처리)
     * @param managerId 매니저ID
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 조건에 맞는 예약 정보를 담은 Page 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ManagerReservationSummaryRspDTO> searchManagerReservationsWithPaging(
            Long managerId, ManagerReservationSearchCondDTO searchCondDTO, Pageable pageable) {
        
        // 조건 및 페이징 포함된 매니저에게 할당된 예약 목록 조회
        Page<ManagerReservationSummaryInfo> searchedReservationPage = managerReservationRepository.searchManagerReservationsWithPaging(managerId, searchCondDTO, pageable);

        // ManagerReservationSummaryInfo -> ManagerReservationSummaryRspDTO 변환
        Page<ManagerReservationSummaryRspDTO> dtoPage = ManagerReservationSummaryRspDTO.fromInfo(searchedReservationPage);

        return dtoPage;
    }

    /**
     * 매니저에게 할당된 예약 상세 조회
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     * @return 매니저 예약 상세 정보 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ManagerReservationRspDTO getManagerReservation(Long managerId, Long reservationId) {

        // 1. 매니저에게 할당된 예약 상세 정보 조회
        ManagerReservationDetailInfo reservationDetail = managerReservationRepository.findByManagerIdAndReservationId(managerId, reservationId);

        // 2. managerDetail에 cancelDate가 있을 때 cancelById로 취소한 사용자 정보를 조회
        User canceledBy;
        if (reservationDetail.getCancelDate() != null) {
            canceledBy = userRepository.findById(reservationDetail.getCanceledById())
                    .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
        } else {
            // 취소되지 않은 경우, canceledBy는 null로 설정
            canceledBy = null;
        }

        // 3. DTO로 매핑
        ManagerReservationRspDTO responseDTO = ManagerReservationRspDTO.fromInfo(reservationDetail, canceledBy);

        return responseDTO;
    }

    /**
     * 매니저가 예약을 수락
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     */
    @Override
    @Transactional
    public void acceptReservation(Long managerId, Long reservationId) {
        // 1. 매니저에게 할당된 예약 조회
        Reservation requestedReservation = managerReservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND_RESERVATION));

        // 2. 매니저와 예약 매칭 정보 조회
        ReservationMatch reservationMatch = reservationMatchRepository.findByReservation_ReservationIdAndManager_UserId(reservationId, managerId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND_RESERVATION_MATCH));

        // 3. 매니저 수락 처리
        requestedReservation.managerAccept();

        // 4. 매니저 예약 매칭 정보 업데이트
        reservationMatch.changeStatus(MatchStatus.MATCHED);

    }

    /**
     * 매니저가 예약을 거절
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     */
    @Override
    @Transactional
    public void rejectReservation(Long managerId, Long reservationId, ReservationCancelReqDTO request) {
        // 1. 매니저에게 할당된 예약 조회
        Reservation requestedReservation = managerReservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND_RESERVATION));

        // 2. 매니저와 예약 매칭 정보 조회
        ReservationMatch reservationMatch = reservationMatchRepository.findByReservation_ReservationIdAndManager_UserId(reservationId, managerId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND_RESERVATION_MATCH));

        // 3. 매니저 거절 처리
        requestedReservation.changeStatus(request.getCancelReason(), ReservationStatus.CANCELED);

        // 4. 매니저 예약 매칭 정보 업데이트
        reservationMatch.changeStatus(MatchStatus.REJECTED);

        // 5. 예약 취소 테이블 생성
        ReservationCancel reservationCancelRecord = ReservationCancel.builder()
                .reservation(requestedReservation)
                .canceledById(managerId)
                .canceledByType(UserRole.MANAGER)
                .cancelReason(request.getCancelReason())
                .build();

        cancelRepository.save(reservationCancelRecord);

    }
}
