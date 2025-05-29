package com.kernel.app.repository;

import com.kernel.common.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerAuthRepository extends JpaRepository<Customer, Long> {

    Boolean existsByPhone(String phone);

    Optional<Customer> findByPhone(String phone);
}
