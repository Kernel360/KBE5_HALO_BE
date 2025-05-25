package com.kernel.admin.repository;

import com.kernel.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Manager, Long> {
}
