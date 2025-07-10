package com.kernel.admin.service;

import com.kernel.admin.service.dto.request.AdminSearchReqDTO;
import com.kernel.admin.service.dto.request.AdminUpdateReqDTO;
import com.kernel.admin.service.dto.response.AdminDetailRspDTO;
import com.kernel.admin.service.dto.response.AdminSearchRspDTO;
import com.kernel.member.service.common.request.UserSignupReqDTO;

import com.kernel.member.service.common.request.UserUpdateReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    // 관리자 회원가입
    void signup(UserSignupReqDTO signupReqDTO);

    // 관리자 목록 조회
    Page<AdminSearchRspDTO> searchAdminList(AdminSearchReqDTO request, Pageable pageable);

    // 관리자 정보 수정
    AdminDetailRspDTO updateAdmin(Long userId, AdminUpdateReqDTO request);

    // 관리자 정보 삭제
    void deleteAdmin(Long userId);
}
