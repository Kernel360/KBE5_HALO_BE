package com.kernel.admin.controller;

import com.kernel.admin.dto.request.AdminCreateRequestDTO;
import com.kernel.admin.service.AdminCreateService;
import com.kernel.common.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCreateController {
    private final AdminCreateService adminCreateService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<null>> createAdmin(
            @RequestBody AdminCreateRequestDTO request
    ) {
        adminCreateService.createAdmin(request);

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 계정이 성공적으로 생성되었습니다.", null));
    }

    @PatchMapping("")
    public ResponseEntity<ApiResponse<null>> resetAdminPassword(
            //@AuthenticationPrincipal String adminId // 로그인 기능 완성 후 사용
    ) {
        adminCreateService.resetAdminPassword(adminId);

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 비밀번호가 성공적으로 초기화되었습니다.", null));
    }

}
