package com.kernel.app.repository;

import com.kernel.common.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CustomerAuthRepository extends JpaRepository<Customer, Long> {

    // 기존 핸드폰 번호 존재 여부 조회
    Boolean existsByPhone(String phone);

    // 핸드폰 번호 기반 수요자 조회
    Optional<Customer> findByPhone(String phone);

    // 수요자 존재 여부 조회(where 핸드폰번호, 이름, 생년월일)
    Boolean existsByPhoneAndUserNameAndBirthDate(String phone, String userName, LocalDate birthDate);

    Optional<Customer> findByPhoneAndUserNameAndBirthDate(String phone, String userName, LocalDate birthDate);
}
