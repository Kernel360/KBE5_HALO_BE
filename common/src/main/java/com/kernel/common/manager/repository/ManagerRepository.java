package com.kernel.common.manager.repository;

import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Page<Manager> findByUserNameContaining(String keyword, Pageable pageable);
    Page<Manager> findByStatus(UserStatus status, Pageable pageable);
}
