package com.kernel.reservation.repository.common;

import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long>, CustomServiceCategoryRepository {

    // 카테고리 조회
    List<ServiceCategory> findByServiceIdIn(List<Long> additionalServiceIds);
}
