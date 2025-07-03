package com.kernel.member.controller;

import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.member.service.AdminCustomerService;
import com.kernel.member.service.CustomerService;
import com.kernel.member.service.request.AdminCustomerSearchReqDTO;
import com.kernel.member.service.response.AdminCustomerSummaryRspDTO;

import com.kernel.member.service.response.CustomerDetailRspDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/customers")
@RequiredArgsConstructor
public class AdminCustomerController {

    private final AdminCustomerService adminCustomerService;
    private final CustomerService customerService;

    /*
     * 관리자 고객 조회 API
     * - 전체 수요자 정보 조회
     * - 페이징 처리
     * - 검색 기능 (이름, 연락처, 이메일, 상태)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminCustomerSummaryRspDTO>>> getCustomers(
            @ModelAttribute @Valid AdminCustomerSearchReqDTO request,
            Pageable pageable
    ) {
        Page<AdminCustomerSummaryRspDTO> result = adminCustomerService.searchCustomers(request, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "전체 수요자 정보 조회 완료", result));
    }

    /*
     * 관리자 수요자 상세 정보 조회 API
     * - 수요자 ID로 상세 정보 조회
     */
    @GetMapping("/{customer-id}")
    public ResponseEntity<ApiResponse<CustomerDetailRspDTO>> getCustomer(
            @PathVariable("customer-id") Long customerId
    ) {
        CustomerDetailRspDTO result = customerService.getCustomer(customerId);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 상세 정보 조회 완료", result));
    }

    /*
     * 관리자 수요자 삭제 API
     * - 수요자 ID로 수요자 삭제
     */
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable("customer-id") Long customerId
    ) {
        adminCustomerService.deleteCustomer(customerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 삭제 완료", null));
    }
}
