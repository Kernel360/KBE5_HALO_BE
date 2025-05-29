package com.kernel.app.repository;

import com.kernel.common.admin.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerAuthRepository extends JpaRepository<Manager, Long> {

    Boolean existsByPhone(String email);

    Optional<Manager> findByPhone(String email);
}
