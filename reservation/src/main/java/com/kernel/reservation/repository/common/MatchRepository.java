package com.kernel.reservation.repository.common;

import com.kernel.reservation.domain.entity.ReservationMatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<ReservationMatch, Long>, CustomMatchRepository {

    // 예약ID로 매치 정보 찾기
    ReservationMatch findByReservation_ReservationId(Long reservationId);



}
