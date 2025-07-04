package com.kernel.payment.repository;

import com.kernel.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // 예약ID로 결제정보 찾기
    Optional<Payment> findByReservation_ReservationId(Long reservationId);
}
