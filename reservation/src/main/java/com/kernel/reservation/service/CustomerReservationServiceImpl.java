package com.kernel.reservation.service;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import com.kernel.member.common.enums.PointChargeType;
import com.kernel.member.service.CustomerService;
import com.kernel.payment.common.enums.PaymentStatus;
import com.kernel.payment.service.PaymentService;
import com.kernel.reservation.common.enums.ReservationErrorCode;
import com.kernel.reservation.common.exception.InsufficientPointsException;
import com.kernel.reservation.common.exception.ReservationException;
import com.kernel.reservation.domain.entity.ReservationCancel;
import com.kernel.reservation.domain.entity.ReservationLocation;
import com.kernel.reservation.domain.entity.ReservationSchedule;
import com.kernel.reservation.repository.CustomerReservationRepository;
import com.kernel.reservation.repository.common.ReservationCancelRepository;
import com.kernel.reservation.repository.common.ReservationLocationRepository;
import com.kernel.reservation.repository.common.ReservationScheduleRepository;
import com.kernel.reservation.repository.common.ReservationUserRepository;
import com.kernel.reservation.service.info.CustomerReservationConfirmInfo;
import com.kernel.reservation.service.info.CustomerReservationDetailInfo;
import com.kernel.reservation.service.info.CustomerReservationSummaryInfo;
import com.kernel.reservation.service.request.CustomerReservationSearchCondDTO;
import com.kernel.reservation.service.request.ReservationCancelReqDTO;
import com.kernel.reservation.service.request.ReservationConfirmReqDTO;
import com.kernel.reservation.service.request.ReservationReqDTO;
import com.kernel.reservation.service.response.CustomerReservationConfirmRspDTO;
import com.kernel.reservation.service.response.CustomerReservationDetailRspDTO;
import com.kernel.reservation.service.response.CustomerReservationSummaryRspDTO;
import com.kernel.reservation.service.response.common.ReservationRspDTO;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.kernel.reservation.common.enums.ReservationErrorCode.INSUFFICIENT_POINTS;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerReservationServiceImpl implements CustomerReservationService {

    private final CustomerReservationRepository customerReservationRepository;
    private final ReservationScheduleRepository ScheduleRepository;
    private final ReservationLocationRepository locationRepository;
    private final ReservationCancelRepository cancelRepository;
    private final ReservationUserRepository userRepository;
    private final ServiceCategoryService serviceCategoryService;
    private final ExtraServiceService extraService;
    private final MatchService matchServiceService;
    private final CustomerService customerService;
    private final PaymentService paymentService;

    /**
     * 예약 요청
     * @param userId 수요자ID
     * @param reqDTO 수요자 예약 요청 DTO
     * @return 예약 요청 내용
     */
    @Override
    @Transactional
    public ReservationRspDTO makeReservationByCustomer(Long userId, ReservationReqDTO reqDTO) {

        // 1. 유저 조회
        User foundUser = userRepository.findByUserIdAndStatus(userId, UserStatus.ACTIVE)
                .orElseThrow(() -> new NoSuchElementException("예약 가능한 유저가 아닙니다."));

        // 2. 서비스 카테고리 조회
        ServiceCategory foundCategory = serviceCategoryService.getServiceCategoryById(reqDTO.getMainServiceId());

        // 3. 예약 저장
        Reservation requestedReservation = customerReservationRepository.save(reqDTO.toReservation(foundUser, foundCategory));

        // 4. 예약 일정 저장
        ReservationSchedule requestedSchedule = ScheduleRepository.save(reqDTO.toSchedule(requestedReservation));

        // 5. 예약 지역 저장
        ReservationLocation requestedLocation = locationRepository.save(reqDTO.toLocation(requestedReservation));

        // 6. 추가 서비스 조회 및 저장
        extraService.saveExtraServices(reqDTO, requestedReservation);

        return ReservationRspDTO.fromEntities(requestedReservation, requestedLocation, requestedSchedule);
    }

    /**
     * 예약 내역 조회
     * @param customerId 수요자ID
     * @param searchCondDTO  검색조건
     * @param pageable   페이징
     * @return 검색 조건에 따른 예약 목록(페이징 포함)
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerReservationSummaryRspDTO> getCustomerReservations(Long customerId, CustomerReservationSearchCondDTO searchCondDTO, Pageable pageable) {

        // 수요자 예약 내역 조회(상태 검색, 페이징, 상세내역 포함)
        Page<CustomerReservationSummaryInfo> result = customerReservationRepository.getCustomerReservations(customerId, searchCondDTO, pageable);

        return result.map(CustomerReservationSummaryRspDTO::fromInfo);

    }

    /**
     * 예약 내역 상세 조회
     * @param userId 수요자ID
     * @param reservationId 예약 ID
     * @return 조회된 예약 상세 정보
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerReservationDetailRspDTO getCustomerReservationDetail(Long userId, Long reservationId) {

        // 수요자 예약 상세 내역 조회
        CustomerReservationDetailInfo result = customerReservationRepository.getCustomerReservationDetail(userId, reservationId);

        return CustomerReservationDetailRspDTO.fromInfo(result);
    }

    /**
     * 예약 취소
     * @param userId 수요자ID
     * @param userRole 로그인 유저 권한
     * @param cancelReqDTO 예약 취소 요청 DTO
     */
    @Override
    @Transactional
    public void cancelReservationByCustomer(Long userId, UserRole userRole, Long reservationId, ReservationCancelReqDTO cancelReqDTO) {

        // 1. 예약 조회
        Reservation foundReservation = customerReservationRepository.getCancelableReservation(reservationId, userId)
                .orElseThrow(() -> new NoSuchElementException("취소 가능한 예약이 존재하지 않습니다."));

        // 2. 예약 상태 변경
        foundReservation.changeStatus(cancelReqDTO.getCancelReason(), ReservationStatus.CANCELED);

        // 3. 예약 취소 저장
        cancelRepository.save(ReservationCancel.builder()
                        .reservation(foundReservation)
                        .canceledById(userId)
                        .canceledByType(userRole)
                        .cancelReason(cancelReqDTO.getCancelReason())
                        .build());

        // 4. 포인트 원복
        customerService.chargePoint(userId, foundReservation.getPrice(), PointChargeType.CANCEL);

        // 5. 결제 취소 저장
        paymentService.changeStatus(foundReservation, PaymentStatus.CANCELED);
    }

    /**
     * 예약 확정 전 취소
     * @param userId 수요자ID
     * @param reservationId 예약ID
     */
    @Override
    @Transactional
    public void cancelBeforeConfirmReservation(Long userId, UserRole userRole, Long reservationId) {

        String cancelReason = "매니저 선택 전 취소";

        // 1. 예약 조회
        Reservation foundReservation = customerReservationRepository.getCancelableReservation(reservationId, userId)
                .orElseThrow(() -> new NoSuchElementException("취소 가능한 예약이 존재하지 않습니다."));

        // 2. 예약 상태 변경
        foundReservation.changeStatus(cancelReason, ReservationStatus.PRE_CANCELED);

        // 3. 예약 취소 저장
        cancelRepository.save(ReservationCancel.builder()
                .reservation(foundReservation)
                .canceledById(userId)
                .canceledByType(userRole)
                .cancelReason(cancelReason)
                .build());

    }

    /**
     * 포인트 검사
     * @param userId 수요자ID
     * @param price 가격
     */
    @Override
    @Transactional(readOnly = true)
    public void validateSufficientPoints(Long userId, Integer price) {

        Integer point = customerService.getCustomerPoints(userId);

        if(point < price)
            throw new InsufficientPointsException(INSUFFICIENT_POINTS);
    }

    /**
     * 예약 확정
     * @param reservationId 예약ID
     * @param userId 로그인한 유저 ID
     * @param confirmReqDTO 예약 확정 DTO
     * @return 확정한 예약 정보
     */
    @Override
    @Transactional
    public CustomerReservationConfirmRspDTO confirmReservation(Long userId, Long reservationId, ReservationConfirmReqDTO confirmReqDTO) {

        // 1. 예약 조회
        Reservation foundReservation = customerReservationRepository.findByReservationIdAndUser_UserId(reservationId, userId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.NOT_FOUND_RESERVATION));

        // 2. 보유 포인트 검사
        validateSufficientPoints(userId, confirmReqDTO.getPayReqDTO().getAmount());

        // 3. 예약 매칭 저장
        matchServiceService.saveReservationMatch(userId, foundReservation, confirmReqDTO.getSelectedManagerId());

        // 4. 보유 포인트 차감
        customerService.payByPoint(userId, confirmReqDTO.getPayReqDTO().getAmount());

        // 5. 결제
        paymentService.processReservationPayment(foundReservation, confirmReqDTO.getPayReqDTO());

        // 6. 예약 요청 조회
        CustomerReservationConfirmInfo rspDTO = customerReservationRepository.getConfirmReservation(reservationId);

        return CustomerReservationConfirmRspDTO.fromInfo(rspDTO);

    }


}
