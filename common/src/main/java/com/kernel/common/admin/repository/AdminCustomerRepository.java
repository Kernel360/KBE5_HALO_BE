package com.kernel.common.admin.repository;

import com.kernel.common.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminCustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByPhone(String phone);
}