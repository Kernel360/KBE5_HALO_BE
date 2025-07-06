package com.kernel.reservation.repository;

import com.kernel.reservation.domain.entity.ServiceCheckLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceCheckLogRepository extends JpaRepository<ServiceCheckLog, Long> {

    // 예약ID로 존재 여부 확인
    Boolean existsByReservation_ReservationId(Long reservationId);

    // 예약ID로 조회
    Optional<ServiceCheckLog> findByReservation_ReservationId(Long reservationId);

    // 매니저의 userId를 기준으로 체크아웃되지 않은 체크인 로그 조회
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Reservation r " +
            "JOIN ServiceCheckLog s ON r = s.reservation " +
            "JOIN ReservationMatch rm ON r = rm.reservation " +
            "WHERE rm.manager.userId = :managerId AND s.inTime IS NOT NULL AND s.outTime IS NULL")
    Boolean existsByManagerUserIdAndInTimeIsNotNullAndOutTimeIsNull(Long managerId);
}
