package com.kernel.common.reservation.service;

import com.kernel.common.manager.entity.Manager;
import com.kernel.common.matching.repository.MatchingManagerRepository;
import com.kernel.common.reservation.dto.mapper.ReservationMapper;
import com.kernel.common.reservation.dto.request.CustomerReservationCancelReqDTO;
import com.kernel.common.reservation.dto.request.CustomerReservationReqDTO;
import com.kernel.common.reservation.dto.request.ReservationConfirmReqDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationRspDTO;
import com.kernel.common.reservation.dto.response.ReservationRspDTO;
import com.kernel.common.reservation.entity.ExtraService;
import com.kernel.common.reservation.entity.Reservation;
import com.kernel.common.reservation.entity.ServiceCategory;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.kernel.common.reservation.repository.CustomerReservationRepository;
import com.kernel.common.reservation.repository.ReservationExtraServiceRepository;
import com.kernel.common.reservation.repository.ReservationServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerReservationServiceImpl implements CustomerReservationService {

    private final CustomerReservationRepository customerReservationRepository;
    private final MatchingManagerRepository matchingManagerRepository;
    private final ReservationExtraServiceRepository extraServiceRepository;
    private final ReservationServiceCategoryRepository serviceCategoryRepository;
    private final ReservationMapper reservationMapper;

    /**
     * 예약 요청
     * @param customerId 수요자ID
     * @param reservationReqDTO 수요자 예약 요청 DTO
     * @return 예약 요청 내용
     */
    @Override
    @Transactional
    public ReservationRspDTO requestCustomerReservation(Long customerId, CustomerReservationReqDTO reservationReqDTO) {

        // 예약 요청 저장
        Reservation requestedReservation = customerReservationRepository.save(reservationMapper.toEntity(customerId, reservationReqDTO));

        // 추가 서비스 저장
        List<ServiceCategory> categories = serviceCategoryRepository.findByServiceIdIn(reservationReqDTO.getAdditionalServiceIds());

        List<ExtraService> extraServices = categories.stream()
                .map(category -> ExtraService.builder()
                        .reservation(requestedReservation)
                        .serviceCategory(category)
                        .build())
                .collect(Collectors.toList());

        extraServiceRepository.saveAll(extraServices);

        return reservationMapper.toRspDTO(requestedReservation);
    }

    /**
     * 예약 확정
     * @param reservationId 예약ID
     * @param customerId 수요자ID
     * @param confirmReqDTO 예약확정 요청DTO
     * @return 확정한 예약 정보
     */
    @Override
    @Transactional
    public CustomerReservationRspDTO confirmCustomerReservation(Long customerId, Long reservationId, ReservationConfirmReqDTO confirmReqDTO) {

        // 예약 조회
        Reservation confirmedReservation = customerReservationRepository.findByReservationIdAndCustomer_CustomerId(reservationId,customerId)
                .orElseThrow(() -> new NoSuchElementException("확정 가능한 예약이 없습니다."));

        // 매니저 조회
        Manager chooseManager = matchingManagerRepository.findByManagerId(confirmReqDTO.getSelectedManagerId())
                .orElseThrow(() -> new NoSuchElementException("예약 할 수 없는 매니저입니다."));

        // 예약 확정
        confirmedReservation.confirmReservation(chooseManager);

        // 예약 재조회
        return customerReservationRepository.getCustomerReservations(customerId, reservationId);

    }

    /**
     * 예약 내역 조회
     * @param customerId 수요자ID
     * @param status     예약 상태
     * @param pageable   페이징
     * @return 검색 조건에 따른 예약 목록(페이징 포함)
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerReservationRspDTO> getCustomerReservations(Long customerId, ReservationStatus status, Pageable pageable) {

        // 수요자 예약 내역 조회(상태 검색, 페이징, 상세내역 포함)
        return customerReservationRepository.getCustomerReservationsByStatus(customerId, status, pageable);

    }

    /**
     * 예약 내역 상세 조회
     * @param customerId 수요자ID
     * @param reservationId 예약 ID
     * @return 조회된 예약 상세 정보
     */
    @Override
    @Transactional(readOnly = true)
    public CustomerReservationDetailRspDTO getCustomerReservationDetail(Long customerId, Long reservationId) {

        // 수요자 예약 상세 내역 조회
        return customerReservationRepository.getCustomerReservationDetail(customerId, reservationId);
    }

    /**
     * 예약 취소
     * @param customerId 수요자ID
     * @param cancelReqDTO 예약 취소 요청 DTO
     */
    @Override
    @Transactional
    public void cancelReservationByCustomer(Long customerId, CustomerReservationCancelReqDTO cancelReqDTO ) {

        // 예약 조회
        Reservation foundReservation
                = customerReservationRepository.findByReservationIdAndCustomer_CustomerId(cancelReqDTO.getReservationId(), customerId)
                .orElseThrow(() -> new NoSuchElementException("취소 가능한 예약이 없습니다."));

        // 예약 취소
        foundReservation.cancelReservation(cancelReqDTO.getCancelReason(), ReservationStatus.CANCELED);

    }

    // 예약 추가서비스 조회
    @Override
    @Transactional(readOnly = true)
    public List<Long> getExtraService(Long reservationId) {

        List<ExtraService> list = extraServiceRepository.findByReservation_ReservationId(reservationId);

        return list.stream()
                .map(extra -> extra.getServiceCategory().getServiceId())
                .collect(Collectors.toList());
    }

}
