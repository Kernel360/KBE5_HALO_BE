package com.kernel.app.repository;

import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.entity.Manager;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerAuthRepository extends JpaRepository<Manager, Long> {

    Boolean existsByPhone(String phone);

    Optional<Manager> findByPhone(String phone);

    Optional<Manager> findByPhoneAndStatusIn(String phone, List<UserStatus> statuses);
}
