package com.kernel.reservation.repository.common;



import com.kernel.reservation.service.info.ServiceCategoryTreeInfo;

import java.util.List;

public interface CustomServiceCategoryRepository {

    // 서비스 카테고리 조회
    List<ServiceCategoryTreeInfo> getServiceCategoryTree();

    // 요청 서비스 카테고리 조회
    ServiceCategoryTreeInfo getRequestServiceCategory(Long serviceId, List<Long> extraServiceIds);
}
