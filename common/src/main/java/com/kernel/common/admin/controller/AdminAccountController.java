package com.kernel.common.admin.controller;


import com.kernel.common.admin.dto.request.AdminAccountReqDTO;
import com.kernel.common.admin.dto.response.AdminResDTO;
import com.kernel.common.admin.service.AdminAccountService;
import com.kernel.common.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAccountController {
    private final AdminAccountService adminAccountService;

    /**
     * 관리자 계정 목록을 조회하는 API
     *
     * @return 관리자 계정 목록
     */
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<AdminResDTO>>> getAdmins(Pageable pageable) {    // 전역적으로 pagination 기본 옵션을 설정해서 별도 설정없이 사용
        Page<AdminResDTO> adminList = adminAccountService.getAdminList(pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 계정 목록이 성공적으로 조회되었습니다.", adminList));
    }

    /**
     * 관리자 계정을 생성하는 API
     *
     * @param request 관리자 계정 생성 요청 DTO
     * @return 성공 메시지와 함께 빈 응답
     */
    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> createAdmin(
            @RequestBody AdminAccountReqDTO request
    ) {
        adminAccountService.createAdmin(request);

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 계정이 성공적으로 생성되었습니다.", null));
    }

    /**
     * 관리자 비밀번호를 초기화하는 API
     *
     * @return 새로 설정된 비밀번호와 함께 성공 메시지
     */
    @PatchMapping("")
    public ResponseEntity<ApiResponse<String>> resetAdminPassword(
            //@AuthenticationPrincipal String adminId // 로그인 기능 완성 후 사용
    ) {
        //String newPassword = adminAccountService.resetAdminPassword(adminId);
        String newPassword = adminAccountService.resetAdminPassword("admin001"); // 임시로 "admin" 사용, 추후에 로그인 기능 완성 후 주석 해제

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 비밀번호가 성공적으로 초기화되었습니다.", newPassword));    // 임시로 클라이언트로 리턴, 추후에 이메일 발송 혹은 현행 유지 결정
    }

}
