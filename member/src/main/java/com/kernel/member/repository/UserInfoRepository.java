package com.kernel.member.repository;

import com.kernel.member.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    // userId, 생년월일 기반 UserInfo 조회 for Id 찾기
    Boolean existsByUserIdAndBirthDate(Long userId, LocalDate birthDate);
}
