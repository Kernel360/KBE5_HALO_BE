package com.kernel.reservation.repository.common;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationUserRepository extends JpaRepository<User, Long> {

    // id, status 기반 user 찾기
    Optional<User> findByUserIdAndStatus(Long userId, UserStatus userStatus);

    // id, status, role 기반 user 찾기
    Optional<User> findByUserIdAndStatusAndRole(Long userId, UserStatus userStatus, UserRole userRole);
}
