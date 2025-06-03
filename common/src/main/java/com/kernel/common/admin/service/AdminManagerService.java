package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.response.AdminManagerSummaryRspDTO;
import com.kernel.common.admin.dto.response.AdminManagerRspDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminManagerService {

    Page<AdminManagerSummaryRspDTO> getManagers(String keyword, Pageable pageable);

    AdminManagerRspDTO getManager(Long managerId);

    Page<AdminManagerSummaryRspDTO> getAppliedManagers(String keyword, Pageable pageable);

    void processAppliedManager(Long managerId, String status);

    Page<AdminManagerSummaryRspDTO> getReportedManagers(String keyword, Pageable pageable);

}
