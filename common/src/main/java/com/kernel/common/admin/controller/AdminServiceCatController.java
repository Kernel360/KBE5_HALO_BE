package com.kernel.common.admin.controller;

import com.kernel.common.admin.dto.request.AdminServiceCatReqDTO;
import com.kernel.common.admin.dto.response.AdminServiceCatRspDTO;
import com.kernel.common.admin.service.AdminSerivceCatService;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/service-categories")
@RequiredArgsConstructor
@Validated
public class AdminServiceCatController {

    private final AdminSerivceCatService adminSerivceCatService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminServiceCatRspDTO>>> getServiceCats() {
        List<AdminServiceCatRspDTO> response = adminSerivceCatService.getServiceCats();

        return ResponseEntity.ok(new ApiResponse<>(true, "서비스 카테고리 조회 성공", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createServiceCat(
            @Valid @RequestBody AdminServiceCatReqDTO request
    ) {
        adminSerivceCatService.createServiceCat(request);

        return ResponseEntity.ok(new ApiResponse<>(true, "서비스 카테고리 생성 성공", null));
    }

    @PatchMapping("/{service-id}")
    public ResponseEntity<ApiResponse<Void>> updateServiceCat(
            @PathVariable("service-id") Long serviceCatId,
            @RequestBody AdminServiceCatReqDTO request
    ) {
        adminSerivceCatService.updateServiceCat(serviceCatId, request);

        return ResponseEntity.ok(new ApiResponse<>(true, "서비스 카테고리 수정 성공", null));
    }

    @DeleteMapping("/{service-id}")
    public ResponseEntity<ApiResponse<Void>> deleteServiceCat (
            @PathVariable("service-id") Long serviceCatId
    ) {
        adminSerivceCatService.deleteServiceCat(serviceCatId);

        return ResponseEntity.ok(new ApiResponse<>(true, "서비스 카테고리 삭제 성공", null));
    }
}
