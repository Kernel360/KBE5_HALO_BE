package com.kernel.reservation.repository;

import com.kernel.reservation.domain.entity.ReservationMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationMatchRepository extends JpaRepository<ReservationMatch, Long> {

    // Define any additional query methods if needed
}
