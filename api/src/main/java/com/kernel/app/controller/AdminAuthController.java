package com.kernel.app.controller;

import com.kernel.app.service.AdminAuthService;
import com.kernel.common.admin.dto.request.AdminSignupReqDTO;
import com.kernel.common.admin.dto.request.AdminUpdateReqDTO;
import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.dto.request.ManagerUpdateReqDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    /**
     * 관리자 회원가입
     * @param joinDTO 관리자 회원가입 요청 DTO
     * @return 성공 응답
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> join(@Valid @RequestBody AdminSignupReqDTO joinDTO){
        adminAuthService.join(joinDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }

    /**
     * 관리자 정보 수정 API
     * @param admin 관리자
     * @param updateReqDTO 매니저 정보 수정 요청 DTO
     * @return null
     */
    @PatchMapping("/{admin-id}")
    public ResponseEntity<ApiResponse<Void>> updateManager(
            @AuthenticationPrincipal AuthenticatedUser admin,
            @Valid @RequestBody AdminUpdateReqDTO updateReqDTO,
            @PathVariable("admin-id") Long adminId
    ) {
        if (   !UserStatus.ACTIVE.equals(admin.getStatus())
                && !UserStatus.REJECTED.equals(admin.getStatus())
        ) {
            throw new AccessDeniedException(
                    "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + admin.getStatus().getLabel() + ")" );
        }
        adminAuthService.updateAdmin(adminId, updateReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 정보 수정 성공", null));
    }

    /**
     * 관리자 삭제 API
     * @param adminId 관리자 ID
     * @return 성공 응답
     */
    @DeleteMapping("/{admin-id}")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(
            @AuthenticationPrincipal AuthenticatedUser admin,
            @PathVariable("admin-id") Long adminId
    ) {
        if (   !UserStatus.ACTIVE.equals(admin.getStatus())
                && !UserStatus.REJECTED.equals(admin.getStatus())
        ) {
            throw new AccessDeniedException(
                    "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + admin.getStatus().getLabel() + ")" );
        }
        adminAuthService.deleteAdmin(adminId);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 삭제 성공", null));
    }

}
