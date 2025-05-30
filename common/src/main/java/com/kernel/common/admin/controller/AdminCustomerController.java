package com.kernel.common.admin.controller;

import com.kernel.common.admin.dto.response.AdminCustomerResDto;
import com.kernel.common.admin.service.AdminCustomerService;
import com.kernel.common.global.entity.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins/customers")
public class AdminCustomerController {

    private final AdminCustomerService adminCustomerService;

    public AdminCustomerController(AdminCustomerService adminCustomerService) {
        this.adminCustomerService = adminCustomerService;
    }

    // 수요자 전체 / 검색 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminCustomerResDto>>> getCustomers(@RequestParam(required = false) String keyword) {
        List<AdminCustomerResDto> result = adminCustomerService.getAllCustomers(keyword);

        return ResponseEntity.ok(new ApiResponse<>(true, "success", result));
    }

    // 상세 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<AdminCustomerResDto>> getCustomer(@PathVariable Long customerId) {
        AdminCustomerResDto result = adminCustomerService.getCustomerDetail(customerId);

        return ResponseEntity.ok(new ApiResponse<>(true, "success", result));
    }

    // 신고 조회
//    @GetMapping("/reported")
//    public ResponseEntity<ApiResponse<List<AdminCustomerResDto>>> getReportCustomers() {
//        List<AdminCustomerResDto> result = adminCustomerService.getReportCustomers();
//
//        return ResponseEntity.ok(new ApiResponse<>(true, "success", result));
//}
}
