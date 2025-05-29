package com.kernel.common.admin.service;



import com.kernel.common.admin.dto.response.AdminManagerSummaryResDTO;
import com.kernel.common.admin.dto.response.ManagerResDTO; // 추후에 ManagerResponseDTO가 정의된 위치로 변경 필요

import java.util.List;

public interface AdminManagerService {

    List<AdminManagerSummaryResDTO> getManagers(String keyword);

    ManagerResDTO getManager(Long managerId);

    List<AdminManagerSummaryResDTO> getApplyManagers(String keyword);

    void processAppliedManager(Long managerId, String status);

    List<AdminManagerSummaryResDTO> getReportedManagers(String keyword);

    ManagerResDTO suspendManager(Long managerId);


}
