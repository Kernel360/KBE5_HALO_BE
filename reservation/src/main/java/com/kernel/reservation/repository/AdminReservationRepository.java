package com.kernel.reservation.repository;

import com.kernel.sharedDomain.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminReservationRepository extends JpaRepository<Reservation, Long>, CustomAdminReservationRepository {
}
