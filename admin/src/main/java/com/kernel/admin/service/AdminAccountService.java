package com.kernel.admin.service;

import com.kernel.admin.service.dto.request.AdminSearchReqDTO;
import com.kernel.admin.service.dto.response.AdminSearchRspDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminAccountService {

    Page<AdminSearchRspDTO> searchAdminList(AdminSearchReqDTO request, Pageable pageable);
}
