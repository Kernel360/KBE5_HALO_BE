package com.kernel.common.reservation.repository;

import com.kernel.common.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // 예약ID로 조회
    Reservation findReservationByreservationId(long reservationId);
}
