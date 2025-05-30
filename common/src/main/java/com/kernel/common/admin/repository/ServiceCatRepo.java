package com.kernel.common.admin.repository;

import com.kernel.common.global.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCatRepo extends JpaRepository<ServiceCategory, Long> {
    // 서비스 카테고리 관련 데이터베이스 작업을 정의합니다.
    // 예: findByName, findByStatus 등
}
