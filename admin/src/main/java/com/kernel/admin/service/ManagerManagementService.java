package com.kernel.admin.service;

import com.kernel.admin.dto.request.AdminManagerStatusRequestDTO;
import com.kernel.admin.dto.response.AdminManagerStatusResponseDTO;
import com.kernel.manager.dto.response.ManagerListResponseDTO;
import com.kernel.manager.dto.response.ManagerResponseDTO;

import java.util.List;

public interface ManagerManagementService {

    List<ManagerListResponseDTO> getManagers(String keyword);

    ManagerResponseDTO getManager(Long managerId);

    List<ManagerListResponseDTO> getApplyManagers(String keyword);

    AdminManagerStatusResponseDTO approveManager(Long managerId, AdminManagerStatusRequestDTO adminManagerStatusRequestDTO);

    AdminManagerStatusResponseDTO rejectManager(Long managerId, AdminManagerStatusRequestDTO adminManagerStatusRequestDTO);

    List<ManagerListResponseDTO> getReportedManagers(String keyword);

    ManagerResponseDTO setSuspendedManager(Long managerId);
}
