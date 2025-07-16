package com.kernel.reservation.repository.common;

import com.kernel.sharedDomain.domain.entity.Reservation;
import com.kernel.sharedDomain.service.response.ReservationManagerMappingInfo;
import com.kernel.sharedDomain.service.response.ReservationScheduleInfo;
import com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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
            "WHERE r.reservationId in :reservationIds " +
            "AND r.user.userId = :userId")
    List<ScheduleAndMatchInfo> findSchedulesAndMatchesByUserIdAndReservationIds(
            @Param("reservationIds") List<Long> reservationIds,
            @Param("userId") Long userId
    );

    // 예약 테이블과 예약 매칭 테이블을 조인해서 매니저 id와 reservationId로 예약 조회
    @Query("SELECT r FROM Reservation r " +
            "JOIN ReservationMatch rm ON r.reservationId = rm.reservation.reservationId " +
            "WHERE r.reservationId = :reservationId " +
            "AND rm.manager.userId = :managerId")
    Optional<Reservation> findReservationByReservationIdAndManagerId(Long reservationId, Long managerId);

    // 주급 정산 가능한 예약 조회
    @Query("SELECT r " +
            "FROM Reservation r " +
            "JOIN ReservationSchedule rs ON r.reservationId = rs.reservationId " +
            "JOIN Payment p ON r.reservationId = p.reservation.reservationId " +
            "WHERE r.status = 'COMPLETED' " +
            "AND rs.requestDate BETWEEN :start AND :end " +
            "AND p.status = 'SUCCESS' " +
            "AND r NOT IN (SELECT s.reservation FROM Settlement s)"
    )
    List<Reservation> findCompletedReservationsWithoutSettlement(@Param("start") LocalDate start, @Param("end") LocalDate end);

    // 예약 ID 기반 매칭 매니저 ID 조회
    @Query("SELECT new com.kernel.sharedDomain.service.response.ReservationManagerMappingInfo(" +
            "rm.reservation.reservationId, rm.manager)" +
            "FROM ReservationMatch rm " +
            "WHERE rm.reservation.reservationId IN :reservationIds"
    )
    List<ReservationManagerMappingInfo> findManagerIdsByReservationIds(@Param("reservationIds") List<Long> reservationIds);

    // 예약 ID 기반 예약 일정 조회
    @Query("SELECT new com.kernel.sharedDomain.service.response.ReservationScheduleInfo(" +
            "r.reservationId, " +
            "rs.requestDate, " +
            "rs.startTime, " +
            "rs.turnaround, " +
            "r.serviceCategory.serviceName" +
            ")  " +
            "FROM Reservation r " +
            "JOIN ReservationSchedule rs ON r.reservationId = rs.reservationId " +
            "WHERE r.reservationId IN :reservationIds"
    )
    List<ReservationScheduleInfo> getReservationSchedule(@Param("reservationIds")List<Long> reservationIds);

    // 이번주 예상 정산 금액 조회 (예약확정, 방문완료)
    @Query("SELECT COALESCE(SUM(r.price), 0L) " +
            "FROM Reservation r " +
            "JOIN ReservationMatch rm ON r.reservationId = rm.reservation.reservationId " +
            "JOIN ReservationSchedule rs ON r.reservationId = rs.reservationId " +
            "JOIN Payment p ON r.reservationId = p.reservation.reservationId " +
            "WHERE rm.manager.userId = :userId " +
            "AND r.status IN ('COMPLETED', 'CONFIRMED') " +
            "AND p.status = 'SUCCESS' " +
            "AND rs.requestDate BETWEEN :startOfWeek AND :endOfWeek "
    )
    Long getThisWeekEstimated(@Param("userId")Long userId,
                               @Param("startOfWeek")LocalDate startOfWeek,
                               @Param("endOfWeek")LocalDate endOfWeek
    );

    // 기간별 정산 금액 조회
    @Query("SELECT COALESCE(SUM(s.totalAmount), 0L) " +
            "FROM Reservation r " +
            "JOIN ReservationMatch rm ON r.reservationId = rm.reservation.reservationId " +
            "JOIN ReservationSchedule rs ON r.reservationId = rs.reservationId " +
            "JOIN Payment p ON r.reservationId = p.reservation.reservationId " +
            "JOIN Settlement s ON r.reservationId = s.reservation.reservationId " +
            "WHERE rm.manager.userId = :userId " +
            "AND r.status = 'COMPLETED'" +
            "AND p.status = 'SUCCESS' " +
            "AND rs.requestDate BETWEEN :start AND :end " +
            "AND s.status = 'SETTLED' "
    )
    Long getSettledAmount(
            @Param("userId")Long userId,
            @Param("start")LocalDate start,
            @Param("end")LocalDate end
    );

    // 날짜 기반 예약 ID 조회
    @Query("SELECT r.reservationId " +
            "FROM Reservation r " +
            "JOIN ReservationMatch rm ON r.reservationId = rm.reservation.reservationId " +
            "JOIN ReservationSchedule rs ON r.reservationId = rs.reservationId " +
            "JOIN Payment p ON r.reservationId = p.reservation.reservationId " +
            "WHERE rm.manager.userId = :userId " +
            "AND r.status = 'COMPLETED'" +
            "AND p.status = 'SUCCESS' " +
            "AND rs.requestDate BETWEEN :start AND :end "
    )
    List<Long> getCompletedReservationIdByScheduleAndManagerId(
            @Param("userId")Long userId,
            @Param("start")LocalDate start,
            @Param("end")LocalDate end
    );

    // 관리자 정산된 예약 조회
    @Query("SELECT new com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo(" +
            "r.reservationId, rs.requestDate, rs.startTime, rs.turnaround, " +
            "rm.manager.userId, rm.status, rm.confirmAt, " +
            "m.user.userName, f.filePathsJson) " +
            "FROM Reservation r " +
            "JOIN ReservationSchedule rs ON r.reservationId = rs.reservationId " +
            "JOIN ReservationMatch rm ON r.reservationId = rm.reservation.reservationId " +
            "JOIN Manager m ON rm.manager.userId = m.userId " +
            "JOIN Payment p ON p.reservation.reservationId = r.reservationId " +
            "JOIN Settlement s ON s.reservation.reservationId = r.reservationId " +
            "LEFT JOIN File f ON f.fileId = m.profileImageFileId.fileId " +
            "WHERE r.status = 'COMPLETED'" +
            "AND p.status = 'SUCCESS'" +
            "AND rs.requestDate BETWEEN :startDate AND :endDate " +
            "AND s.status = 'SETTLED' ")
    List<ScheduleAndMatchInfo> getReservationForSettlementByAdmin(
            @Param("startDate")LocalDate startDate,
            @Param("endDate")LocalDate endDate
    );

    // 이번주 예상 정산 금액 조회 (예약확정, 방문완료)
    @Query("SELECT COALESCE(SUM(r.price), 0L) " +
            "FROM Reservation r " +
            "JOIN ReservationMatch rm ON r.reservationId = rm.reservation.reservationId " +
            "JOIN ReservationSchedule rs ON r.reservationId = rs.reservationId " +
            "JOIN Payment p ON r.reservationId = p.reservation.reservationId " +
            "WHERE r.status IN ('COMPLETED', 'CONFIRMED') " +
            "AND p.status = 'SUCCESS' " +
            "AND rs.requestDate BETWEEN :start AND :end "
    )
    Long getThisWeekEstimatedForAdmin(
              @Param("start")LocalDate start,
              @Param("end")LocalDate end
    );

    // userId 제외 기간별 정산 금액 조회
    @Query("SELECT r.reservationId "+
            "FROM Reservation r " +
            "JOIN ReservationMatch rm ON r.reservationId = rm.reservation.reservationId " +
            "JOIN ReservationSchedule rs ON r.reservationId = rs.reservationId " +
            "JOIN Payment p ON r.reservationId = p.reservation.reservationId " +
            "JOIN Settlement s ON r.reservationId = s.reservation.reservationId " +
            "WHERE r.status = 'COMPLETED'" +
            "AND p.status = 'SUCCESS' " +
            "AND rs.requestDate BETWEEN :start AND :end " +
            "AND s.status = 'SETTLED' "
    )
    List<Long> getSettledAmountWithoutUserId(
            @Param("start")LocalDate start,
            @Param("end")LocalDate end
    );
}
