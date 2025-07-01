package com.kernel.member.repository;

import com.kernel.member.service.common.info.AdminManagerDetailInfo;
import com.kernel.member.service.common.info.ManagerSummaryInfo;
import com.kernel.member.service.request.AdminManagerSearchReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomManagerRepository {
    Page<ManagerSummaryInfo> searchManagers(AdminManagerSearchReqDTO request, Pageable pageable);
    AdminManagerDetailInfo getAdminManagerDetailInfo(Long managerId);
}
