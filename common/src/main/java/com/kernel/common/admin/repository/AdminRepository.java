package com.kernel.common.admin.repository;

import com.kernel.common.admin.dto.request.AdminSearchReqDTO;
import com.kernel.common.admin.entity.Admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>, CustomAdminRepository {
}
