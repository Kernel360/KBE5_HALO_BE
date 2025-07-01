package com.kernel.member.controller;

import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.member.service.AdminManagerService;

import com.kernel.member.service.request.AdminManagerSearchReqDTO;
import com.kernel.member.service.response.AdminManagerRspDTO;
import com.kernel.member.service.response.AdminManagerSummaryRspDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "AdminManagerController", description = "관리자 매니저 관리 API")
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
}
