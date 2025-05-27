package com.kernel.common.admin.repository;

import com.kernel.common.admin.entity.Manager;  // 추후에 Manager entity가 정의된 위치로 변경 필요
import com.kernel.common.admin.entity.Status;   // 추후에 Status entity가 정의된 위치로 변경 필요

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    List<Manager> findByUserNameContaining(String keyword);
    List<Manager> findByStatus(Status status);
}
