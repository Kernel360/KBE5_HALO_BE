package com.kernel.common.reservation.repository;

import com.kernel.common.reservation.dto.response.CustomerReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationRspDTO;
import com.kernel.common.reservation.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomCustomerReservationRepository {

    // 수요자 예약 내역 조회(상태검색, 페이징 포함)
    Page<CustomerReservationRspDTO> getCustomerReservationsByStatus(Long customerId, ReservationStatus status, Pageable pageable);

    // 수요자 예약 상세 내역 조회
    CustomerReservationDetailRspDTO getCustomerReservationDetail(Long customerId, Long reservationId);

    // 수요자 예약 조회
    CustomerReservationRspDTO getCustomerReservations(Long customerId, Long reservationId);
}
