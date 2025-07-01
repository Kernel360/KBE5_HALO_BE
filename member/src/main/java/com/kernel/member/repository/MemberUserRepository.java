package com.kernel.member.repository;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberUserRepository extends JpaRepository<User, Long> {

    // phone 기반 사용자 존재 확인
    Boolean existsByPhone(String phone);

    // status, id 기반 사용자 조회
    Optional<User> findByUserIdAndStatus(Long userId, UserStatus userStatus);

    // 사용자 찾기
    Optional<User> findByPhoneAndUserNameAndStatus(String phone, String userName, UserStatus userStatus);



}
