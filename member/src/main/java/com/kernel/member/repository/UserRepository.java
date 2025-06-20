package com.kernel.member.repository;

import com.kernel.global.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByPhone(String phone);
}
