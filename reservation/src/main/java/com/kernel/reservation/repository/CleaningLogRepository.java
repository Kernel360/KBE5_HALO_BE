package com.kernel.reservation.repository;

import com.kernel.reservation.domain.entity.CleaningLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleaningLogRepository extends JpaRepository<CleaningLog, Long> {

    // 예약ID로 존재 여부 확인
    Boolean existsByReservation_ReservationId(Long reservationId);

    // 예약ID로 조회
    CleaningLog findByReservation_ReservationId(Long reservationId);
}
