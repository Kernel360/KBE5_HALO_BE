package com.kernel.reservation.repository;


import com.kernel.reservation.domain.info.ServiceCategoryTreeInfo;

import java.util.List;

public interface CustomServiceCategoryRepository {

    // 서비스 카테고리 조회
    List<ServiceCategoryTreeInfo> getServiceCategoryTree();

    // 요청 서비스 카테고리 조회
    ServiceCategoryTreeInfo getRequestServiceCategory(Long serviceId, List<Long> extraServiceIds);
}
