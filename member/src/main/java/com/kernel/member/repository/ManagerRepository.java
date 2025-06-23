package com.kernel.member.repository;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.domain.entity.Manager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    // TODO: 추후 admin에서 manager 조회 시 필요한 쿼리, 기존 코드에서 CustomeManagerRepository로 분리된 부분을 확인하고 필요에 따라 구현
    /*Page<Manager> findByStatusNot(UserStatus status, Pageable pageable);
    Page<Manager> findByUserNameContainingAndStatusNot(String keyword, UserStatus status, Pageable pageable);
    Page<Manager> findByUserNameContaining(String keyword, Pageable pageable);
    Page<Manager> findByStatus(UserStatus status, Pageable pageable);*/

}
