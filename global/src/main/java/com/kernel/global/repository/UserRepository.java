package com.kernel.global.repository;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 핸드폰 번호로 활성 유저 찾기
    Optional<User> findByPhoneAndStatus(String phone, UserStatus status);

}
