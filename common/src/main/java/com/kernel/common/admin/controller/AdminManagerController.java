package com.kernel.common.admin.controller;

import com.kernel.common.admin.dto.response.ManagerResponseDTO; // 추후에 ManagerResponseDTO가 정의된 위치로 변경 필요
import com.kernel.common.admin.entity.Status;   // 추후에 Status entity가 정의된 위치로 변경 필요
import com.kernel.common.admin.service.AdminManagerService;
import com.kernel.common.entity.ApiResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/managers")
@RequiredArgsConstructor
public class AdminManagerController {

    private final AdminManagerService adminManagerService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ManagerResponseDTO>>> getManagers(
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @RequestParam(required = false) String keyword
    ) {
        List<ManagerResponseDTO> responseList = adminManagerService.getManagers(keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", responseList));
    }

    @GetMapping("/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerResponseDTO>> getManager(
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @PathVariable("manager_id") Long managerId
    ) {
        ManagerResponseDTO response = adminManagerService.getManager(managerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", response));
    }

    @GetMapping("/applies")
    public ResponseEntity<ApiResponse<List<ManagerResponseDTO>>> getAppliedManagers (
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @RequestParam(required = false) String keyword
    ) {
        List<ManagerResponseDTO> reponseList = adminManagerService.getApplyManagers(keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", reponseList));
    }

    @PostMapping("/applies/{manager_id}/approve")
    public ResponseEntity<ApiResponse<Void>> approveManager(    // 변경 -> Post 메서드에서 리턴 값을 null 변경
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @PathVariable("manager_id") Long managerId
    ) {
        adminManagerService.approveManager(managerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }

    @PostMapping("/applies/{manager_id}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectManager(     // 변경 -> Post 메서드에서 리턴 값을 null 변경
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @PathVariable("manager_id") Long managerId
    ) {
        adminManagerService.rejectManager(managerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }

    @GetMapping("/reported")
    public ResponseEntity<ApiResponse<List<ManagerResponseDTO>>> getReportedManagers(
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @RequestParam(required = false) String keyword
    ) {
        List<ManagerResponseDTO> responseList = adminManagerService.getReportedManagers(keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", responseList));
    }

    @PatchMapping("/reported/{manager_id}/black")
    public ResponseEntity<ApiResponse<ManagerResponseDTO>> suspendManager(
            // AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @PathVariable("manager_id") Long managerId
    ) {
       ManagerResponseDTO response = adminManagerService.setSuspendedManager(managerId);
       return ResponseEntity.ok(new ApiResponse<>(true, "success", response));
    }

}
