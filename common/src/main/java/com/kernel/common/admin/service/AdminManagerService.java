package com.kernel.common.admin.service;



import com.kernel.common.admin.dto.response.AdminManagerSummaryRspDTO;
import com.kernel.common.admin.dto.response.ManagerRspDTO; // 추후에 ManagerResponseDTO가 정의된 위치로 변경 필요

import java.util.List;

public interface AdminManagerService {

    List<AdminManagerSummaryRspDTO> getManagers(String keyword);

    ManagerRspDTO getManager(Long managerId);

    List<AdminManagerSummaryRspDTO> getApplyManagers(String keyword);

    void processAppliedManager(Long managerId, String status);

    List<AdminManagerSummaryRspDTO> getReportedManagers(String keyword);

    ManagerRspDTO suspendManager(Long managerId);


}
