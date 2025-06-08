package com.kernel.common.reservation.repository;

import com.kernel.common.reservation.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationServiceCategoryRepository extends JpaRepository<ServiceCategory, Long>, CustomServiceCategoryRepository {

    // 카테고리 조회
    List<ServiceCategory> findByServiceIdIn(List<Long> additionalServiceIds);
}
