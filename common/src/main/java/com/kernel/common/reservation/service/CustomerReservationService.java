package com.kernel.common.reservation.service;


import com.kernel.common.reservation.dto.request.CustomerReservationCancelReqDTO;
import com.kernel.common.reservation.dto.request.CustomerReservationReqDTO;
import com.kernel.common.reservation.dto.request.ReservationConfirmReqDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationRspDTO;
import com.kernel.common.reservation.dto.response.ReservationRspDTO;
import com.kernel.common.reservation.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerReservationService {

    // 수요자 예약 요청
    ReservationRspDTO requestCustomerReservation(Long customerId, CustomerReservationReqDTO reservationReqDTO);

    // 예약 확정
    CustomerReservationRspDTO confirmCustomerReservation(Long customerId, Long reservationId, ReservationConfirmReqDTO confirmReqDTO);

    // 수요자 예역 내역 검색 및 조회
    Page<CustomerReservationRspDTO> getCustomerReservations(Long customerId, ReservationStatus status, Pageable pageable);

    // 수요자 예약 내역 상세 조회
    CustomerReservationDetailRspDTO getCustomerReservationDetail(Long customerId, Long reservationId);

    // 수요자 예약 취소
    void cancelReservationByCustomer(Long customerId, CustomerReservationCancelReqDTO cancelReqDTO);

    // 수요자 추가 서비스 조회
    List<Long> getExtraService(Long reservationId);
}
