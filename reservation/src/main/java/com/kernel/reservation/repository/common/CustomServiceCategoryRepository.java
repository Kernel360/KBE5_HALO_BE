package com.kernel.reservation.repository.common;



import com.kernel.sharedDomain.domain.entity.ServiceCategory;

import java.util.List;

public interface CustomServiceCategoryRepository {

    // 요청 서비스 카테고리 조회
    List<ServiceCategory> getRequestServiceCategory(Long serviceId, List<Long> extraServiceIds);
}
