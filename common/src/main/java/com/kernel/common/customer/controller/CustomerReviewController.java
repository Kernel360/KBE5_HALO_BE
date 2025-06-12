package com.kernel.common.customer.controller;

import com.kernel.common.customer.dto.request.CustomerReviewReqDTO;
import com.kernel.common.customer.dto.response.CustomerReviewRspDTO;
import com.kernel.common.customer.service.CustomerReviewService;
import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/reviews")
@RequiredArgsConstructor
public class CustomerReviewController {

    private final CustomerReviewService reviewService;

    /**
     * 수요자 리뷰 목록 조회
     * @param customer 수요자ID
     * @param pageable 페이징
     * @return reviewRspDTO
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerReviewRspDTO>>> getCustomerReviews(
            @AuthenticationPrincipal AuthenticatedUser customer,
            @PageableDefault(size=5) Pageable pageable
    ){
        Page<CustomerReviewRspDTO> rspDTOPage = reviewService.getCustomerReviews(customer.getUserId(), pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 목록 조회 성공", rspDTOPage));
    }

    /**
     * 수요자 리뷰 조회 by 예약ID
     * @param customer 수요자ID
     * @param reservationId 예약ID
     * @return reviewRspDTO
     */
    @GetMapping("{reservation-id}")
    public ResponseEntity<ApiResponse<CustomerReviewRspDTO>> getCustomerReviewsByReservationId(
            @AuthenticationPrincipal AuthenticatedUser customer,
            @PathVariable("reservation-id") Long reservationId
    ){
        CustomerReviewRspDTO rspDTO = reviewService.getCustomerReviewsByReservationId(customer.getUserId(), reservationId);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 조회 성공", rspDTO));
    }

    /**
     * 수요자 리뷰 등록
     * @param reservationId 예약ID
     * @param customer 수요자ID
     * @param reviewReqDTO 리뷰요청DTO
     * @return reviewRspDTO
     */
    @PostMapping("{reservation-id}")
    public ResponseEntity<ApiResponse<CustomerReviewRspDTO>> createCustomerReview(
            @PathVariable("reservation-id") Long reservationId,
            @AuthenticationPrincipal AuthenticatedUser customer,
            @Valid @RequestBody CustomerReviewReqDTO reviewReqDTO
    ){
        CustomerReviewRspDTO rspDTO = reviewService.createOrUpdateCustomerReview(customer.getUserId() ,reservationId, reviewReqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 등록 성공", rspDTO));
    }

    /**
     * 수요자 리뷰 수정
     * @param reservationId 예약ID
     * @param customer 수요자ID
     * @param reviewReqDTO 리뷰요청DTO
     * @return reviewRspDTO
     */
    @PatchMapping("{reservation-id}")
    public ResponseEntity<ApiResponse<CustomerReviewRspDTO>> updateCustomerReview(
            @PathVariable("reservation-id") Long reservationId,
            @AuthenticationPrincipal AuthenticatedUser customer,
            @Valid @RequestBody CustomerReviewReqDTO reviewReqDTO
    ){
        CustomerReviewRspDTO rspDTO = reviewService.createOrUpdateCustomerReview(customer.getUserId() ,reservationId, reviewReqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 수정 성공", rspDTO));
    }
}
