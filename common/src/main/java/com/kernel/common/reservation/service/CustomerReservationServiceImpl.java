package com.kernel.common.reservation.service;

import com.kernel.common.reservation.dto.request.CustomerReservationCancelReqDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationRspDTO;
import com.kernel.common.reservation.entity.Reservation;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.kernel.common.reservation.repository.CustomerReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerReservationServiceImpl implements CustomerReservationService {

    private final CustomerReservationRepository customerReservationRepository;

    /**
     * 예약 내역 조회
     *
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
}
