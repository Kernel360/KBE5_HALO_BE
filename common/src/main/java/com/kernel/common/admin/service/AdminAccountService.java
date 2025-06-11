package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.request.AdminSearchReqDTO;
import com.kernel.common.admin.dto.response.AdminSearchRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminAccountService {

    Page<AdminSearchRspDTO> searchAdminList(AdminSearchReqDTO request, Pageable pageable);
}
