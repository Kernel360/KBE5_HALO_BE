package com.kernel.common.admin.service;



import com.kernel.common.admin.dto.response.ManagerResponseDTO; // 추후에 ManagerResponseDTO가 정의된 위치로 변경 필요

import java.util.List;

public interface AdminManagerService {

    List<ManagerResponseDTO> getManagers(String keyword);

    ManagerResponseDTO getManager(Long managerId);

    List<ManagerResponseDTO> getApplyManagers(String keyword);

    void processAppliedManager(Long managerId, String status);

    List<ManagerResponseDTO> getReportedManagers(String keyword);

    ManagerResponseDTO suspendManager(Long managerId);


}
