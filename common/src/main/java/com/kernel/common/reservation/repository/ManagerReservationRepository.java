package com.kernel.common.reservation.repository;

import com.kernel.common.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerReservationRepository extends JpaRepository<Reservation, Long> {
}
