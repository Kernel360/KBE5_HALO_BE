package com.kernel.reservation.repository;

import com.kernel.reservation.service.info.CustomerReservationDetailInfo;
import com.kernel.reservation.service.info.CustomerReservationSummaryInfo;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface CustomCustomerReservationRepository {

    // 수요자 예약 내역 조회(상태검색, 페이징 포함)
    Page<CustomerReservationSummaryInfo> getCustomerReservationsByStatus(Long customerId, ReservationStatus status, Pageable pageable);

    // 수요자 예약 상세 내역 조회
    CustomerReservationDetailInfo getCustomerReservationDetail(Long customerId, Long reservationId);

}
