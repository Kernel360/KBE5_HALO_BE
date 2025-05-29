package com.kernel.common.admin.repository;

import com.kernel.common.admin.entity.Admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Page<Admin> findAll(Pageable pageable); // 관리자 계정 목록 조회, Pagination 적용
    Optional<Admin> findById(String id);    // 일반적으로 id는 Long 타입이지만 해당 id는 실제 로그인을 위한 식별자로 사용되므로 String 타입으로 설정
    boolean existsById(@NotBlank @Size(max = 50) String id);

}
