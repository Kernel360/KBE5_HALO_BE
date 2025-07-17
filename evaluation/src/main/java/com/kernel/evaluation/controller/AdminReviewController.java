package com.kernel.evaluation.controller;

import com.kernel.evaluation.service.review.CustomerReviewService;
import com.kernel.evaluation.service.review.ManagerReviewService;
import com.kernel.evaluation.service.review.dto.request.ManagerReviewSearchCondDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewSearchReqDTO;
import com.kernel.evaluation.service.review.dto.response.CustomerReviewRspDTO;
import com.kernel.evaluation.service.review.dto.response.ManagerReviewPageRspDTO;
import com.kernel.global.service.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin Manager Review API", description = "관리자 매니저 리뷰 API")
@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final ManagerReviewService managerReviewService;
    private final CustomerReviewService customerReviewService;

    @Schema(description = "매니저 리뷰 조회 API")
    @GetMapping("/{manager-id}")
    public ResponseEntity<ApiResponse<Page<ManagerReviewPageRspDTO>>> getManagerReviews(
            @PathVariable("manager-id") Long managerId,
            @ModelAttribute ManagerReviewSearchCondDTO request,
            @PageableDefault(size = 3, page = 0) Pageable pageable
    ) {
        Page<ManagerReviewPageRspDTO> response = managerReviewService.searchManagerReviewsWithPaging(managerId, request, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 리뷰 조회 성공", response));
    }

    @GetMapping("/customer/{customer-id}")
    public ResponseEntity<ApiResponse<Page<CustomerReviewRspDTO>>> getCustomerReviews(
            @PathVariable("customer-id") Long customerId,
            @ModelAttribute ReviewSearchReqDTO request,
            @PageableDefault(size = 3, page = 0) Pageable pageable
    ) {
        Page<CustomerReviewRspDTO> response = customerReviewService.getCustomerReviews(customerId, request, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "고객 리뷰 조회 성공", response));
    }

}