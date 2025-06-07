package com.kernel.common.reservation.controller;

import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.reservation.dto.response.ServiceCategoryTreeDTO;
import com.kernel.common.reservation.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers/reservations")
public class ServiceCategoryController {

    private final ServiceCategoryService serviceCategoryService;

    /**
     * 서비스 카테고리 조회
     * @return 서비스 카테고리 tree
     */
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<ServiceCategoryTreeDTO>>> getServiceCategory(){

        List<ServiceCategoryTreeDTO> rspDTOList = serviceCategoryService.getServiceCategoryTree();

        return ResponseEntity.ok(new ApiResponse<>(true, "서비스 카테고리 조회 성공", rspDTOList));
    }
}
