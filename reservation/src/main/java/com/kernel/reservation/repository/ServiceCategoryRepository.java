package com.kernel.reservation.repository;

import com.kernel.reservation.domain.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long>, CustomServiceCategoryRepository {

    // 카테고리 조회
    List<ServiceCategory> findByServiceIdIn(List<Long> additionalServiceIds);
}
