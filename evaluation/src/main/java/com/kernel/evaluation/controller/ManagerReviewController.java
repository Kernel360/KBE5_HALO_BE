package com.kernel.evaluation.controller;


import com.kernel.evaluation.service.review.ManagerReviewService;
import com.kernel.evaluation.service.review.dto.request.ManagerReviewSearchCondDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewCreateReqDTO;
import com.kernel.evaluation.service.review.dto.response.ManagerReviewPageRspDTO;
import com.kernel.evaluation.service.review.dto.response.ManagerReviewRspDTO;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/managers/reviews")
@RequiredArgsConstructor
public class ManagerReviewController {

    public final ManagerReviewService managerReviewService;

    /**
     * 매니저 리뷰 목록 조회 (검색 조건 및 페이징 처리)
     * @param user 로그인한 유저
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 검색 조건에 따른 매니저 리뷰 목록을 응답 (페이징 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ManagerReviewPageRspDTO>>> searchManagerReviews(
        @AuthenticationPrincipal CustomUserDetails user,
        @ModelAttribute ManagerReviewSearchCondDTO searchCondDTO,
        Pageable pageable
    ) {
        if (!UserStatus.ACTIVE.equals(user.getStatus())) { // 활성
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + user.getStatus() + ")" );
        }
        Page<ManagerReviewPageRspDTO> summaryRspDTOPage
            = managerReviewService.searchManagerReviewsWithPaging(user.getUserId(), searchCondDTO, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 리뷰 목록 조회 성공", summaryRspDTOPage));
    }

    /**
     * 매니저 리뷰 등록 API
     * @param user 로그인한 유저
     * @param reservationId 예약ID
     * @param requestDTO 매니저 리뷰 등록 요청 데이터
     * @return 작성된 리뷰 정보를 담은 응답
     */
    @PostMapping("/{reservation-id}")
    public ResponseEntity<ApiResponse<ManagerReviewRspDTO>> createManagerReview(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable("reservation-id") Long reservationId,
        @Valid @RequestBody ReviewCreateReqDTO requestDTO
    ) {
        if (!UserStatus.ACTIVE.equals(user.getStatus())) { // 활성
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + user.getStatus() + ")" );
        }
        ManagerReviewRspDTO responseDTO = managerReviewService.createManagerReview(user.getUserId(), reservationId, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 리뷰 등록 성공", responseDTO));
    }
}
