package com.kernel.reservation.repository.common;

import com.kernel.reservation.domain.entity.ReservationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationScheduleRepository extends JpaRepository<ReservationSchedule, Long> {
}
