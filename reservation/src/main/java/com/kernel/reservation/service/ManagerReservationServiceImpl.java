package com.kernel.reservation.service;

import com.kernel.global.common.enums.ErrorCode;
import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.exception.AuthException;
import com.kernel.global.domain.entity.User;
import com.kernel.global.repository.UserRepository;
import com.kernel.reservation.common.enums.ReservationErrorCode;
import com.kernel.reservation.common.enums.ServiceCheckLogErrorCode;
import com.kernel.reservation.common.exception.ReservationException;
import com.kernel.reservation.common.exception.ServiceCheckLogException;
import com.kernel.reservation.domain.entity.ReservationCancel;
import com.kernel.reservation.domain.entity.ReservationMatch;
import com.kernel.reservation.domain.entity.ServiceCheckLog;
import com.kernel.reservation.repository.ManagerReservationRepository;
import com.kernel.reservation.repository.ReservationMatchRepository;
import com.kernel.reservation.repository.ServiceCheckLogRepository;
import com.kernel.reservation.repository.common.ReservationCancelRepository;
import com.kernel.reservation.service.info.ManagerReservationDetailInfo;
import com.kernel.reservation.service.info.ManagerReservationSummaryInfo;
import com.kernel.reservation.service.request.ServiceCheckInReqDTO;
import com.kernel.reservation.service.request.ManagerReservationSearchCondDTO;
import com.kernel.reservation.service.request.ReservationCancelReqDTO;
import com.kernel.reservation.service.request.ServiceCheckOutReqDTO;
import com.kernel.reservation.service.response.ServiceCheckInRspDTO;
import com.kernel.reservation.service.response.ManagerReservationRspDTO;
import com.kernel.reservation.service.response.ManagerReservationSummaryRspDTO;
import com.kernel.reservation.service.response.ServiceCheckOutRspDTO;
import com.kernel.sharedDomain.common.enums.MatchStatus;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ManagerReservationServiceImpl implements ManagerReservationService {

    private final UserRepository userRepository;
    private final ManagerReservationRepository managerReservationRepository;
    private final ReservationMatchRepository reservationMatchRepository;
    private final ReservationCancelRepository cancelRepository;
    private final ServiceCheckLogRepository serviceCheckLogRepository;

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

    /**
     * 체크인
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     * @param serviceCheckInReqDTO 체크인요청DTO
     * @return 체크인 정보를 담은 응답
     */
    @Override
    @Transactional
    public ServiceCheckInRspDTO checkIn(Long managerId, Long reservationId, ServiceCheckInReqDTO serviceCheckInReqDTO) {

        // 1. 예약 조회
        Reservation reservation = managerReservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND_RESERVATION));

        // 2. 체크인 로그 존재 여부 확인
        if (serviceCheckLogRepository.existsByReservation_ReservationId(reservationId)) {
            throw new ServiceCheckLogException(ServiceCheckLogErrorCode.CHECK_IN_ALREADY_EXISTS);
        }

        // 3. 체크인 로그 생성
        ServiceCheckLog checkLog = ServiceCheckLog.builder()
                .reservation(reservation)
                .build();

        // 4. 체크인 로그 기록
        checkLog.checkIn(serviceCheckInReqDTO.getInFileId());

        // 5. 체크인 로그 저장
        serviceCheckLogRepository.save(checkLog);

        // 6. 예약 상태를 IN_PROGRESS로 변경
        reservation.checkIn();

        // 7. Entity -> ResponseDTO 변환 후, return
        return ServiceCheckInRspDTO.toDTO(checkLog);
    }

    /**
     * 체크아웃
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     * @param cleaningLogCheckOutReqDTO 체크아웃요청DTO
     * @return 체크아웃 정보를 담은 응답
     */
    @Override
    @Transactional
    public ServiceCheckOutRspDTO checkOut(Long managerId, Long reservationId, ServiceCheckOutReqDTO cleaningLogCheckOutReqDTO) {

        // 1. 예약 조회
        Reservation reservation = managerReservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND_RESERVATION));

        // 2. 체크인 로그 조회
        ServiceCheckLog checkLog = serviceCheckLogRepository.findByReservation_ReservationId(reservationId)
                .orElseThrow(() -> new ServiceCheckLogException(ServiceCheckLogErrorCode.CHECK_IN_NOT_FOUND));

        // 3. 체크아웃 로그 존재 여부 확인
        if (checkLog.getOutTime() != null) {
            throw new ServiceCheckLogException(ServiceCheckLogErrorCode.CHECK_OUT_ALREADY_EXISTS);
        }

        // 4. 체크아웃 로그 기록
        checkLog.checkOut(cleaningLogCheckOutReqDTO.getOutFileId());

        // 6. 예약 상태를 COMPLETED로 변경
        reservation.checkOut();

        // 7. Entity -> ResponseDTO 변환 후, return
        return ServiceCheckOutRspDTO.toDTO(checkLog);
    }
}
