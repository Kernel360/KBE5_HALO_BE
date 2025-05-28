package com.kernel.app.repository;

import com.kernel.app.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminAuthRepository extends JpaRepository<Admin, Long> {

    Boolean existsByPhone(String email);

    Optional<Admin> findByPhone(String email);
}
