package com.kernel.evaluation.controller;

import com.kernel.evaluation.service.review.CustomerReviewService;
import com.kernel.evaluation.service.review.dto.request.ReviewCreateReqDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewUpdateReqDTO;
import com.kernel.evaluation.service.review.dto.response.CustomerReviewRspDTO;
import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
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
     * @param user 로그인 유저
     * @param pageable 페이징
     * @return reviewRspDTO
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerReviewRspDTO>>> getCustomerReviews(
            @AuthenticationPrincipal CustomUserDetails user,
            @PageableDefault(size=5) Pageable pageable
    ){
        Page<CustomerReviewRspDTO> rspDTOPage = reviewService.getCustomerReviews(user.getUserId(), pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 목록 조회 성공", rspDTOPage));
    }

    /**
     * 수요자 리뷰 조회 by 예약ID
     * @param user 로그인 유저
     * @param reservationId 예약ID
     * @return reviewRspDTO
     */
    @GetMapping("{reservation-id}")
    public ResponseEntity<ApiResponse<CustomerReviewRspDTO>> getCustomerReviewsByReservationId(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("reservation-id") Long reservationId
    ){
        CustomerReviewRspDTO rspDTO = reviewService.getCustomerReviewsByReservationId(user.getUserId(), reservationId);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 조회 성공", rspDTO));
    }

    /**
     * 수요자 리뷰 등록
     * @param reservationId 예약ID
     * @param user 로그인 유저
     * @param createReqDTO 리뷰요청DTO
     * @return reviewRspDTO
     */
    @PostMapping("{reservation-id}")
    public ResponseEntity<ApiResponse<CustomerReviewRspDTO>> createCustomerReview(
            @PathVariable("reservation-id") Long reservationId,
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ReviewCreateReqDTO createReqDTO
    ){
        CustomerReviewRspDTO rspDTO = reviewService.createCustomerReview(user.getUserId() ,reservationId, createReqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 등록 성공", rspDTO));
    }

    /**
     * 수요자 리뷰 수정
     * @param reviewId 리뷰ID
     * @param user 로그인 유저
     * @param updateReqDTO 리뷰요청DTO
     * @return reviewRspDTO
     */
    @PatchMapping("{review-id}")
    public ResponseEntity<ApiResponse<CustomerReviewRspDTO>> updateCustomerReview(
            @PathVariable("review-id") Long reviewId,
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ReviewUpdateReqDTO updateReqDTO
    ){
        CustomerReviewRspDTO rspDTO = reviewService.updateCustomerReview(user.getUserId(), reviewId, updateReqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 리뷰 수정 성공", rspDTO));
    }
}
