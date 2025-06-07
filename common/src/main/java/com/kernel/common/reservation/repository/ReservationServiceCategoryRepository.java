package com.kernel.common.reservation.repository;

import com.kernel.common.reservation.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationServiceCategoryRepository extends JpaRepository<ServiceCategory, Long>, CustomServiceCategoryRepository {
}
