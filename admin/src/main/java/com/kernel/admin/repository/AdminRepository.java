package com.kernel.admin.repository;

import com.kernel.global.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<User, Long>, CustomAdminRepository {

    // 핸드폰 번호로 관리자 찾기
    Optional<User> findByPhone(String phone);
}
