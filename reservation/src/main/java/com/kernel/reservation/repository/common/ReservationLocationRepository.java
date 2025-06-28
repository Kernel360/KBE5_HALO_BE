package com.kernel.reservation.repository.common;

import com.kernel.reservation.domain.entity.ReservationLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationLocationRepository extends JpaRepository<ReservationLocation, Long> {
}
