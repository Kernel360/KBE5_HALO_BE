package com.kernel.app.repository;

import com.kernel.app.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerAuthRepository extends JpaRepository<Customer, Long> {

    Boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);
}
