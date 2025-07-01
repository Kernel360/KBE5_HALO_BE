package com.kernel.reservation.repository.common;

import com.kernel.reservation.domain.entity.ReservationCancel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationCancelRepository extends JpaRepository<ReservationCancel, Long> {
}
