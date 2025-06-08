package com.kernel.common.reservation.repository;

import com.kernel.common.reservation.entity.ExtraService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationExtraServiceRepository extends JpaRepository<ExtraService, Long> {
    List<ExtraService> findByReservation_ReservationId(Long reservationId);
}
