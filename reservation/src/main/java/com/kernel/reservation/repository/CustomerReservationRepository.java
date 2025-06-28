package com.kernel.reservation.repository;


import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerReservationRepository extends JpaRepository<Reservation, Long>, CustomCustomerReservationRepository{

    // 수요자 예약 취소
    Optional<Reservation> findByReservationIdAndUser_UserId(Long reservationId, Long userId);

}
