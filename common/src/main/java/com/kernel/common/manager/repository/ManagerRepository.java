package com.kernel.common.manager.repository;

import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.entity.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long>, CustomManagerRepository {
    Page<Manager> findByStatusNot(UserStatus status, Pageable pageable);
    Page<Manager> findByUserNameContainingAndStatusNot(String keyword, UserStatus status, Pageable pageable);
    Page<Manager> findByUserNameContaining(String keyword, Pageable pageable);
    Page<Manager> findByStatus(UserStatus status, Pageable pageable);

}
