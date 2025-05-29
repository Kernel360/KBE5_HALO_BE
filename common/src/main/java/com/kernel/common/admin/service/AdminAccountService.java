package com.kernel.common.admin.service;


import com.kernel.common.admin.dto.request.AdminAccountReqDTO;
import com.kernel.common.admin.dto.response.AdminResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminAccountService {
    // TODO: 관리자 계정을 생성 service 인터페이스 정의
    Page<AdminResDTO> getAdminList(Pageable pageable);  // 관리자 계정 목록 조회, Pagination 적용
    void createAdmin(AdminAccountReqDTO request);
    String resetAdminPassword(String id);
}
