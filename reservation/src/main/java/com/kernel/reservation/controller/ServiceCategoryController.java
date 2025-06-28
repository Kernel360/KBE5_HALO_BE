package com.kernel.reservation.controller;

import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.reservation.service.ServiceCategoryService;
import com.kernel.reservation.service.response.common.ServiceCategoryTreeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/common/serviceCategory")
@RequiredArgsConstructor
public class ServiceCategoryController {

    private ServiceCategoryService serviceCategoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceCategoryTreeDTO>>> getAllServiceCategory() {

        List<ServiceCategoryTreeDTO> result = serviceCategoryService.getServiceCategoryTree();

        return ResponseEntity.ok(new ApiResponse<>(true, "서비스 카테고리 조회 성공", result));

    }
}
