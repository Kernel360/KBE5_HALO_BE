package com.kernel.common.admin.repository;

import com.kernel.common.admin.entity.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
