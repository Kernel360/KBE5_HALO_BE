package com.kernel.reservation.service;


import com.kernel.global.common.enums.UserRole;
import com.kernel.reservation.service.request.ReservationCancelReqDTO;
import com.kernel.reservation.service.request.ReservationReqDTO;
import com.kernel.reservation.service.response.CustomerReservationDetailRspDTO;
import com.kernel.reservation.service.response.CustomerReservationSummaryRspDTO;
import com.kernel.reservation.service.response.common.ReservationRspDTO;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerReservationService {

    // 수요자 예약 요청
    ReservationRspDTO makeReservationByCustomer(Long customerId, ReservationReqDTO reservationReqDTO);

    // 수요자 예역 내역 검색 및 조회
    Page<CustomerReservationSummaryRspDTO> getCustomerReservations(Long customerId, ReservationStatus status, Pageable pageable);

    // 수요자 예약 내역 상세 조회
    CustomerReservationDetailRspDTO getCustomerReservationDetail(Long customerId, Long reservationId);

    // 수요자 예약 취소
    void cancelReservationByCustomer(Long customerId, UserRole userRole, ReservationCancelReqDTO cancelReqDTO);

    // 예약 확정 전 취소
    void cancelBeforeConfirmReservation(Long userId, UserRole role, Long reservationId);
}
