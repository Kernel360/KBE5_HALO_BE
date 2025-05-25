package com.kernel.admin.service;

import com.kernel.admin.dto.request.AdminCreateRequestDTO;

public interface AdminCreateService {
    // TODO: 관리자 계정을 생성 service 인터페이스 정의
    void createAdmin(AdminCreateRequestDTO request);
    void resetAdminPassword(String adminId);
}
