package com.kernel.common.admin.service;



import com.kernel.common.admin.dto.response.AdminManagerSummaryResponseDTO;
import com.kernel.common.admin.dto.response.ManagerResponseDTO; // 추후에 ManagerResponseDTO가 정의된 위치로 변경 필요

import java.util.List;

public interface AdminManagerService {

    List<AdminManagerSummaryResponseDTO> getManagers(String keyword);

    ManagerResponseDTO getManager(Long managerId);

    List<AdminManagerSummaryResponseDTO> getApplyManagers(String keyword);

    void processAppliedManager(Long managerId, String status);

    List<AdminManagerSummaryResponseDTO> getReportedManagers(String keyword);

    ManagerResponseDTO suspendManager(Long managerId);


}
