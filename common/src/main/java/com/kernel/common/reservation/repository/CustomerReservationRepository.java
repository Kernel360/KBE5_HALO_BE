package com.kernel.common.reservation.repository;

import com.kernel.common.reservation.entity.Reservation;
import com.kernel.common.reservation.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerReservationRepository extends JpaRepository<Reservation, Long>, CustomCustomerReservationRepository{

    // 수요자 예약 취소
    Optional<Reservation> findByReservationIdAndCustomer_CustomerId(Long reservationId, Long customerId);

    // 예약 조회(where managerId, customerId) 수요자 피드백 등록 시 사용
    Optional<Reservation> findByCustomer_CustomerIdAndManager_ManagerId(Long customerId, Long managerId);

    // 예약 조회(where reservationId, customerId, status) 수요자 리뷰 조회 시 사용
    boolean existsByReservationIdAndCustomer_CustomerIdAndStatus(Long reservationId, Long customerId, ReservationStatus reservationStatus);
}
