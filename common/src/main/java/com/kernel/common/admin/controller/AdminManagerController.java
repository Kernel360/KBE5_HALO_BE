package com.kernel.common.admin.controller;

import com.kernel.common.admin.dto.response.AdminManagerSummaryResDTO;
import com.kernel.common.admin.dto.response.ManagerResDTO;
import com.kernel.common.admin.service.AdminManagerService;
import com.kernel.common.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/managers")
@RequiredArgsConstructor
@Validated
public class AdminManagerController {

    private final AdminManagerService adminManagerService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<AdminManagerSummaryResDTO>>> getManagers(
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @RequestParam(required = false) String keyword
    ) {
        List<AdminManagerSummaryResDTO> responseList = adminManagerService.getManagers(keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", responseList));
    }

    @GetMapping("/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerResDTO>> getManager(
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @PathVariable("manager_id") Long managerId
    ) {
        ManagerResDTO response = adminManagerService.getManager(managerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", response));
    }

    @GetMapping("/applies")
    public ResponseEntity<ApiResponse<List<AdminManagerSummaryResDTO>>> getAppliedManagers (
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @RequestParam(required = false) String keyword
    ) {
        List<AdminManagerSummaryResDTO> reponseList = adminManagerService.getApplyManagers(keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", reponseList));
    }

    @PatchMapping("/applies/{manager_id}")
    public ResponseEntity<ApiResponse<Void>> processAppliedManager(    // 변경 -> Post 메서드에서 리턴 값을 null 변경
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @PathVariable("manager_id") Long managerId,
            @RequestParam String status
    ) {
        adminManagerService.processAppliedManager(managerId, status);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }

    @GetMapping("/reported")
    public ResponseEntity<ApiResponse<List<AdminManagerSummaryResDTO>>> getReportedManagers(
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @RequestParam(required = false) String keyword
    ) {
        List<AdminManagerSummaryResDTO> responseList = adminManagerService.getReportedManagers(keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", responseList));
    }

    @PatchMapping("/reported/{manager_id}/black")
    public ResponseEntity<ApiResponse<ManagerResDTO>> suspendManager(
            // AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @PathVariable("manager_id") Long managerId
    ) {
       ManagerResDTO response = adminManagerService.suspendManager(managerId);
       return ResponseEntity.ok(new ApiResponse<>(true, "success", response));
    }

}
