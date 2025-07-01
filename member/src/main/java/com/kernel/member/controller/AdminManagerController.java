package com.kernel.member.controller;

import com.kernel.common.admin.dto.request.AdminManagerSearchReqDTO;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.admin.dto.response.AdminManagerSummaryRspDTO;
import com.kernel.common.admin.dto.response.AdminManagerRspDTO;
import com.kernel.common.admin.service.AdminManagerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/admin/managers")
public class AdminManagerController {

    private final AdminManagerService adminManagerService;

    // 전체 매니저 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminManagerSummaryRspDTO>>> getManagers(
            @ModelAttribute @Valid AdminManagerSearchReqDTO request,
            Pageable pageable
    ) {
        System.out.println(pageable.toString());
        Page<AdminManagerSummaryRspDTO> responsePage = adminManagerService.getManagers(request, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 목록 조회 성공", responsePage));
    }

    // 선택한 매니저 상세 정보 조회
    @GetMapping("/{manager_id}")
    public ResponseEntity<ApiResponse<AdminManagerRspDTO>> getManager(
            @PathVariable("manager_id") Long managerId
    ) {
        AdminManagerRspDTO response = adminManagerService.getManager(managerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 상세 정보 조회 성공", response));
    }

    // 매니저 신청 목록 조회
    @GetMapping("/applies")
    public ResponseEntity<ApiResponse<Page<AdminManagerSummaryRspDTO>>> getAppliedManagers (
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {
        Page<AdminManagerSummaryRspDTO> reponsePage = adminManagerService.getAppliedManagers(keyword, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 신청 목록 조회 성공", reponsePage));
    }

    // 매니저 신청 승인/반려
    @PatchMapping("/applies/{manager_id}")
    public ResponseEntity<ApiResponse<Void>> processAppliedManager(
            @PathVariable("manager_id") Long managerId,
            @RequestBody Map<String, String> userStatus
            ) {
        adminManagerService.processAppliedManager(managerId, userStatus.get("status"));
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 신청 처리 성공", null));
    }

    // 매니저 강제 종료
    @PatchMapping("/terminate/{manager_id}")
    public ResponseEntity<ApiResponse<Void>> terminateManager(
            @PathVariable("manager_id") Long managerId
    ) {
        adminManagerService.terminateManager(managerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 계약 해지 성공", null));
    }

    // 신고된 매니저 목록 조회
    @GetMapping("/suspended")
    public ResponseEntity<ApiResponse<Page<AdminManagerSummaryRspDTO>>> getReportedManagers(
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {
        Page<AdminManagerSummaryRspDTO> responsePage = adminManagerService.getReportedManagers(keyword, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "신고된 매니저 목록 조회 성공", responsePage));
    }
}
