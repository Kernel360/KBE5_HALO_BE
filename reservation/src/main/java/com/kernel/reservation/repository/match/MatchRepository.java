package com.kernel.reservation.repository.match;

import com.kernel.global.domain.entity.User;
import com.kernel.reservation.domain.entity.ReservationMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<ReservationMatch, Long>, CustomMatchRepository {

    // 매니저 조회 for 수요자 예약 확정
    Optional<User> findByManagerId(Long selectedManagerId);

    // 매칭 매니저 리스트 조회
    List<User> findByManagerIdIn(List<Long> matchedManagerIds);

    // 예약ID 기반 매칭 조회
    ReservationMatch findByReservation_ReservationId(Long reservationId);
}
