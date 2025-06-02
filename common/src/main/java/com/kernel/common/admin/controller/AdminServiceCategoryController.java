package com.kernel.common.admin.controller;

import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.admin.dto.request.AdminServiceCategoryReqDTO;
import com.kernel.common.admin.dto.response.AdminServiceCategoryRspDTO;
import com.kernel.common.admin.service.AdminServiceCategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/service-categories")
public class AdminServiceCategoryController {

    private final AdminServiceCategoryService adminServiceCategoryService;

    // 서비스 카테고리 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminServiceCategoryRspDTO>>> getServiceCategories() {
        List<AdminServiceCategoryRspDTO> response = adminServiceCategoryService.getServiceCategories();

        return ResponseEntity.ok(new ApiResponse<>(true, "서비스 카테고리 조회 성공", response));
    }

    // 서비스 카테고리 생성
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createServiceCategory(
            @Valid @RequestBody AdminServiceCategoryReqDTO request
    ) {
        adminServiceCategoryService.createServiceCategory(request);

        return ResponseEntity.ok(new ApiResponse<>(true, "서비스 카테고리 생성 성공", null));
    }

    // 서비스 카테고리 수정
    @PatchMapping("/{service-id}")
    public ResponseEntity<ApiResponse<Void>> updateServiceCategory(
            @PathVariable("service-id") Long serviceCategoryId,
            @RequestBody AdminServiceCategoryReqDTO request
    ) {
        adminServiceCategoryService.updateServiceCategory(serviceCategoryId, request);

        return ResponseEntity.ok(new ApiResponse<>(true, "서비스 카테고리 수정 성공", null));
    }

    // 서비스 카테고리 삭제
    @DeleteMapping("/{service-id}")
    public ResponseEntity<ApiResponse<Void>> deleteServiceCategory (
            @PathVariable("service-id") Long serviceCatId
    ) {
        adminServiceCategoryService.deleteServiceCategory(serviceCatId);

        return ResponseEntity.ok(new ApiResponse<>(true, "서비스 카테고리 삭제 성공", null));
    }
}
