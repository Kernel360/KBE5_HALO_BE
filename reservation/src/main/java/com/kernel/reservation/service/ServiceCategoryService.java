package com.kernel.reservation.service;


import com.kernel.reservation.service.response.common.ServiceCategoryTreeDTO;
import com.kernel.sharedDomain.domain.entity.ServiceCategory;

import java.util.List;

public interface ServiceCategoryService {

    // 서비스 카테고리 조회
    List<ServiceCategoryTreeDTO> getServiceCategoryTree();

    // 서비스 카테고리 List 조회
    List<ServiceCategory> getServiceCategoriesById(List<Long> serviceCategoryId);

    // 서비스 카테고리 조회
    ServiceCategory getServiceCategoryById(Long serviceCategoryId);

    ServiceCategoryTreeDTO getRequestServiceCategory(Long mainServiceId, List<Long> extraServiceId);

}