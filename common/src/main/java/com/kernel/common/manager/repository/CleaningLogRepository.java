package com.kernel.common.manager.repository;

import com.kernel.common.manager.entity.CleaningLog;
import com.kernel.common.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleaningLogRepository extends JpaRepository<CleaningLog, Long> {

    // 예약ID로 존재 여부 확인
    Boolean existsByReservation_ReservationId(Long reservationId);

    // 예약ID로 조회
    CleaningLog findByReservation_ReservationId(Long reservationId);
}
