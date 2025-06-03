package com.kernel.common.reservation.service;


import com.kernel.common.reservation.dto.request.CustomerReservationCancelReqDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationRspDTO;
import com.kernel.common.reservation.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerReservationService {

    // 수요자 예역 내역 검색 및 조회
    Page<CustomerReservationRspDTO> getCustomerReservations(Long customerId, ReservationStatus status, Pageable pageable);

    // 수요자 예약 내역 상세 조회
    CustomerReservationDetailRspDTO getCustomerReservationDetail(Long customerId, Long reservationId);

    // 수요자 예약 취소
    void cancelReservationByCustomer(Long customerId, CustomerReservationCancelReqDTO cancelReqDTO);
}
