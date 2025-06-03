package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.request.AdminServiceCategoryReqDTO;
import com.kernel.common.admin.dto.response.AdminServiceCategoryRspDTO;

import java.util.List;

public interface AdminServiceCategoryService {

    List<AdminServiceCategoryRspDTO> getServiceCategories();
    void createServiceCategory(AdminServiceCategoryReqDTO request);
    void updateServiceCategory(Long serviceCatId, AdminServiceCategoryReqDTO request);
    void deleteServiceCategory(Long serviceCatId);

}
