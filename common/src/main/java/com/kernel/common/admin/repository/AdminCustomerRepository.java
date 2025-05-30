package com.kernel.common.admin.repository;

import com.kernel.common.admin.entity.AdminCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminCustomerRepository extends JpaRepository<AdminCustomer, Long> {
    List<AdminCustomer> findByCustomerContaining(String keyword);
}
