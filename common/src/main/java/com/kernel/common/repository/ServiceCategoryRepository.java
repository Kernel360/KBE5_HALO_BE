package com.kernel.common.repository;

import com.kernel.common.reservation.entity.ServiceCategory;
import com.kernel.common.reservation.repository.CustomServiceCategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long>, CustomServiceCategoryRepository {
}
