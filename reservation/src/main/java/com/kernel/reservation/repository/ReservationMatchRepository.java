package com.kernel.reservation.repository;

import com.kernel.reservation.domain.entity.ReservationMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationMatchRepository extends JpaRepository<ReservationMatch, Long> {

    Optional<ReservationMatch> findByReservation_ReservationIdAndManager_UserId(Long reservationId, Long managerId);
}
