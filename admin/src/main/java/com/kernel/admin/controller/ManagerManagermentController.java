package com.kernel.admin.controller;

import com.kernel.admin.dto.request.AdminManagerStatusRequestDTO;
import com.kernel.admin.dto.response.AdminManagerStatusResponseDTO;
import com.kernel.admin.service.ManagerManagementService;
import com.kernel.common.entity.ApiResponse;
import com.kernel.manager.dto.response.ManagerListResponseDTO;
import com.kernel.manager.dto.response.ManagerResponseDTO;
import com.kernel.manager.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/managers")
@RequiredArgsConstructor
public class ManagerManagermentController {

    private final ManagerManagementService managerManagementService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ManagerListResponseDTO>>> getManagers(
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @RequestParam(required = false) String keyword
    ) {
        List<ManagerListResponseDTO> responseList = managerManagementService.getManagers(keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, "성공", responseList));
    }

    @GetMapping("/{manager_id}")
    public ResponseEntity<ApiResponse<ManagerResponseDTO>> getManager(
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @PathVariable("manager_id") Long managerId
    ) {
        ManagerResponseDTO response = managerManagementService.getManager(managerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "성공", response));
    }

    @GetMapping("/applies")
    public ResponseEntity<ApiResponse<List<ManagerListResponseDTO>>> getApplyManagers (
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @RequestParam(required = false) String keyword
    ) {
        List<ManagerListResponseDTO> reponseList = managerManagementService.getApplyManagers(keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, "성공", reponseList));
    }

    @PostMapping("/applies/{manager_id}")
    public ResponseEntity<ApiResponse<AdminManagerStatusResponseDTO>> approveRejectManager(
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @PathVariable("manager_id") Long managerId,
            @RequestBody AdminManagerStatusRequestDTO adminManagerStatusRequestDTO
            ) {
        AdminManagerStatusResponseDTO response;

        if (adminManagerStatusRequestDTO.getStatus() == Status.ACTIVE) { // Status는 Manager 모듈에서 정의된 enum 타입이라고 가정
            response = managerManagementService.approveManager(managerId, adminManagerStatusRequestDTO);
        } else {
            response = managerManagementService.rejectManager(managerId, adminManagerStatusRequestDTO);
        }

        return ResponseEntity.ok(new ApiResponse<>(true, "성공", response));
    }

    @GetMapping("/reported")
    public ResponseEntity<ApiResponse<List<ManagerListResponseDTO>>> getReportedManagers(
            // @AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @RequestParam(required = false) String keyword
    ) {
        List<ManagerListResponseDTO> responseList = managerManagementService.getReportedManagers(keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, "성공", responseList));
    }

    @PatchMapping("/reported/{manager_id}/black")
    public ResponseEntity<ApiResponse<ManagerResponseDTO>> setSuspendedManager(
            // AuthenticationPrincipal -> 로그인 기능 완성시 사용 예정
            @PathVariable("manager_id") Long managerId
    ) {
       ManagerResponseDTO response = managerManagementService.setSuspendedManager(managerId);
       return ResponseEntity.ok(new ApiResponse<>(true, "성공", response));
    }

}
