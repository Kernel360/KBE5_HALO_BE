package com.kernel.admin.controller;

import com.kernel.admin.service.AdminService;
import com.kernel.admin.service.dto.request.AdminSearchReqDTO;
import com.kernel.admin.service.dto.request.AdminUpdateReqDTO;
import com.kernel.admin.service.dto.response.AdminDetailRspDTO;
import com.kernel.admin.service.dto.response.AdminSearchRspDTO;
import com.kernel.global.domain.entity.User;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.member.service.common.request.UserSignupReqDTO;
import com.kernel.member.service.common.request.UserUpdateReqDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin API", description = "관리자 관련 API")
@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 관리자 회원가입 API
     *
     * @param signupReqDTO 관리자 회원가입 요청 DTO
     * @return ApiResponse 객체에 성공 메시지와 상태 코드를 포함하여 반환
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(
            @AuthenticationPrincipal User admin,
            @RequestBody @Valid UserSignupReqDTO signupReqDTO
    ) {
        adminService.signup(signupReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 회원가입 성공", null));
    }

    /**
     * 관리자 목록 조회 API
     *
     * @param request  검색 조건을 포함하는 요청 DTO
     * @param pageable 페이징 정보
     * @return 검색된 관리자 목록과 페이징 정보가 포함된 ApiResponse 객체
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminSearchRspDTO>>> getAdminList(
            @ModelAttribute @Valid AdminSearchReqDTO request,
            Pageable pageable
    ) {

        Page<AdminSearchRspDTO> adminPage = adminService.searchAdminList(request, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 목록 조회 성공", adminPage));
    }

    /**
     * 관리자 정보 수정 API
     *
     * @param userId      수정할 관리자 ID
     * @param request 관리자 정보 수정 요청 DTO
     * @return 수정된 관리자 정보가 포함된 ApiResponse 객체
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<AdminDetailRspDTO>> updateAdmin(
            @PathVariable Long userId,
            @RequestBody @Valid AdminUpdateReqDTO request
            ) {
        AdminDetailRspDTO updatedAdmin = adminService.updateAdmin(userId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 정보 수정 성공", updatedAdmin));
    }

    /**
     * 관리자 정보 삭제 API
     *
     * @param userId 삭제할 관리자 ID
     * @return ApiResponse 객체에 성공 메시지와 상태 코드를 포함하여 반환
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(
            @PathVariable Long userId
    ) {
        adminService.deleteAdmin(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 정보 삭제 성공", null));
    }
}
