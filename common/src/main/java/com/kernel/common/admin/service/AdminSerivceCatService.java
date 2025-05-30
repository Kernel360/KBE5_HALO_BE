package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.request.AdminServiceCatReqDTO;
import com.kernel.common.admin.dto.response.AdminServiceCatRspDTO;

import java.util.List;

public interface AdminSerivceCatService {


    List<AdminServiceCatRspDTO> getServiceCats();
    void createServiceCat(AdminServiceCatReqDTO request);
    void updateServiceCat(Long serviceCatId, AdminServiceCatReqDTO request);
    void deleteServiceCat(Long serviceCatId);

}
