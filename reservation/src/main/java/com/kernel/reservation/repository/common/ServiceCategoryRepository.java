package com.kernel.reservation.repository.common;

import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long>, CustomServiceCategoryRepository {

    // 서비스 카테고리 전체 조회
    List<ServiceCategory> findAllByOrderByCreatedAtAsc();

    // 서비스 카테고리 이름으로 조회
    List<ServiceCategory> findByServiceNameIn(List<String> serviceNames);
}
