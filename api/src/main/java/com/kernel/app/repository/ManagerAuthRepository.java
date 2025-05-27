package com.kernel.app.repository;

import com.kernel.app.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerAuthRepository extends JpaRepository<Manager, Long> {

    Boolean existsByEmail(String email);

    Optional<Manager> findByEmail(String email);
}
