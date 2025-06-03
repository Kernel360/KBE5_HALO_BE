package com.kernel.common.reservation.repository;

import com.kernel.common.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerReservationRepository extends JpaRepository<Reservation, Long>, CustomCustomerReservationRepository{

    // 수요자 예약 취소
    Optional<Reservation> findByReservationIdAndCustomer_CustomerId(Long reservationId, Long customerId);
}
