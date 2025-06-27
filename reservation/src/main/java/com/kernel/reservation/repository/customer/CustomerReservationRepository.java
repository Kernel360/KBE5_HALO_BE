package com.kernel.reservation.repository.customer;


import com.kernel.reservation.domain.entity.Reservation;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerReservationRepository extends JpaRepository<Reservation, Long>, CustomCustomerReservationRepository{

    // 수요자 예약 취소
    Optional<Reservation> findByReservationIdAndUser_UserId(Long reservationId, Long userId);

    // 예약 조회(where managerId, customerId) 수요자 피드백 등록 시 사용
    Optional<Reservation> findByCustomer_CustomerIdAndManager_ManagerId(Long customerId, Long managerId);

    // 예약 조회(where reservationId, customerId, status) 수요자 리뷰 조회 시 사용
    boolean existsByReservationIdAndCustomer_CustomerIdAndStatus(Long reservationId, Long customerId, ReservationStatus reservationStatus);
}
