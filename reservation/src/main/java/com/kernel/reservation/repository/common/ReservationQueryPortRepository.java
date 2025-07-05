package com.kernel.reservation.repository.common;

import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationQueryPortRepository extends JpaRepository<Reservation, Long> {

    // 예약 일정 및 매칭 조회
    @Query("SELECT new com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo(" +
            "r.reservationId, rs.requestDate, rs.startTime, rs.turnaround, " +
            "rm.manager.userId, rm.status, rm.confirmAt, " +
            "m.user.userName, f.filePathsJson) " +
            "FROM Reservation r " +
            "JOIN ReservationSchedule rs ON r.reservationId = rs.reservationId " +
            "JOIN ReservationMatch rm ON r.reservationId = rm.reservation.reservationId " +
            "JOIN Manager m ON rm.manager.userId = m.userId " +
            "JOIN User u ON m.userId = u.userId " +
            "LEFT JOIN File f ON m.profileImageFileId.fileId = f.fileId " +
            "WHERE r.reservationId = :reservationId " +
            "AND r.user.userId = :userId")
    Optional<ScheduleAndMatchInfo> findScheduleAndMatchByReservationIdAndUserId(
            @Param("reservationId") Long reservationId,
            @Param("userId") Long userId
    );

    // 예약 조회
    Optional<Reservation> findByReservationIdAndUser_UserId(Long reservationId, Long userId);

    // 예약 일정 및 매칭 조회 List
    @Query("SELECT new com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo(" +
            "r.reservationId, rs.requestDate, rs.startTime, rs.turnaround, " +
            "rm.manager.userId, rm.status, rm.confirmAt, " +
            "m.user.userName, f.filePathsJson) " +
            "FROM Reservation r " +
            "JOIN ReservationSchedule rs ON r.reservationId = rs.reservationId " +
            "JOIN ReservationMatch rm ON r.reservationId = rm.reservation.reservationId " +
            "JOIN Manager m ON rm.manager.userId = m.userId " +
            "LEFT JOIN File f ON f.fileId = m.profileImageFileId.fileId " +
            "WHERE r.status = :status " +
            "AND r.user.userId = :userId")
    List<ScheduleAndMatchInfo> findSchedulesAndMatchesByUserIdAndStatus(
            @Param("status") ReservationStatus status,
            @Param("userId") Long userId
    );

}
