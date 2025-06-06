package com.kernel.common.customer.controller;

import com.kernel.common.customer.dto.request.CustomerReviewCreateReqDTO;
import com.kernel.common.customer.dto.response.CustomerReviewRspDTO;
import com.kernel.common.customer.service.CustomerReviewService;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/reviews")
@RequiredArgsConstructor
public class CustomerReviewController {

    private final CustomerReviewService reviewService;

    /**
     * 수요자 리뷰 목록 조회
     * @param customerId 수요자ID
     * @param pageable 페이징
     * @return reviewRspDTO
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerReviewRspDTO>>> getCustomerReviews(
            @RequestParam Long customerId,
            // TODO @AuthenticatedUser AuthenticatedUser customer
            @PageableDefault(size=10) Pageable pageable
    ){
        Page<CustomerReviewRspDTO> rspDTOPage = reviewService.getCustomerReviews(customerId,pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 목록 조회 성공", rspDTOPage));
    }

    /**
     * 수요자 리뷰 조회 by 예약ID
     * @param customerId 수요자ID
     * @param reservationId 예약ID
     * @return reviewRspDTO
     */
    @GetMapping("{reservation-id}")
    public ResponseEntity<ApiResponse<CustomerReviewRspDTO>> getCustomerReviewsByReservationId(
            @RequestParam Long customerId,
            // TODO @AuthenticatedUser AuthenticatedUser customer
            @PathVariable("reservation-id") Long reservationId
    ){
        CustomerReviewRspDTO rspDTO = reviewService.getCustomerReviewsByReservationId(customerId,reservationId);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 조회 성공", rspDTO));
    }

    /**
     * 수요자 리뷰 등록/수정
     * @param reservationId 예약ID
     * @param customerId 수요자ID
     * @param reviewCreateReqDTO 리뷰등록요청DTO
     * @return reviewRspDTO
     */
    @PostMapping("{reservation-id}")
    public ResponseEntity<ApiResponse<CustomerReviewRspDTO>> createOrUpdateCustomerReview(
            @PathVariable("reservation-id") Long reservationId,
            // TODO @AuthenticatedUser AuthenticatedUser customer
            @RequestParam Long customerId,
            @Valid @RequestBody CustomerReviewCreateReqDTO reviewCreateReqDTO
    ){
        CustomerReviewRspDTO rspDTO = reviewService.createOrUpdateCustomerReview(customerId,reservationId, reviewCreateReqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 등록 성공", rspDTO));
    }
}
