package com.kernel.common.reservation.service;

import com.kernel.common.reservation.dto.response.ServiceCategoryTreeDTO;

import java.util.List;

public interface ServiceCategoryService {

    // 서비스 카테고리 조회
    List<ServiceCategoryTreeDTO> getServiceCategoryTree();

    // 요청 서비스 카테고리 조회
    ServiceCategoryTreeDTO getRequestServiceCategory(Long mainServiceId, List<Long> extraServiceId);
}
