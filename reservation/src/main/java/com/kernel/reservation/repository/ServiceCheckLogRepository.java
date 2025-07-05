package com.kernel.reservation.repository;

import com.kernel.reservation.domain.entity.ServiceCheckLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceCheckLogRepository extends JpaRepository<ServiceCheckLog, Long> {

    // 예약ID로 존재 여부 확인
    Boolean existsByReservation_ReservationId(Long reservationId);

    // 예약ID로 조회
    Optional<ServiceCheckLog> findByReservation_ReservationId(Long reservationId);
}
