package com.kernel.member.service;

import com.kernel.member.service.request.AdminManagerSearchReqDTO;
import com.kernel.member.service.response.AdminManagerRspDTO;
import com.kernel.member.service.response.AdminManagerSummaryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminManagerService {

    Page<AdminManagerSummaryRspDTO> getManagers(AdminManagerSearchReqDTO request, Pageable pageable);

    AdminManagerRspDTO getManager(Long managerId);

    void processAppliedManager(Long managerId, String status);

    void terminateManager(Long managerId);
}
