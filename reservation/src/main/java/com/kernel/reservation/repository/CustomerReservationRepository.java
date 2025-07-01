package com.kernel.reservation.repository;


import com.kernel.sharedDomain.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerReservationRepository extends JpaRepository<Reservation, Long>, CustomCustomerReservationRepository{

    // 수요자 취소 가능 예약 조회
    @Query("SELECT r FROM Reservation r " +
            "WHERE r.reservationId = :reservationId " +
            "AND r.user.userId = :userId " +
            "AND (r.status = 'REQUESTED' OR r.status = 'CONFIRMED')")
    Optional<Reservation> getCancelableReservation(@Param ("reservationId")Long reservationId, @Param("userId") Long userId);

    // 예약 조회
    Optional<Reservation> findByReservationIdAndUser_UserId(Long reservationId, Long userId);
}
