package com.kernel.common.manager.controller;

import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.dto.request.ManagerReviewReqDTO;
import com.kernel.common.manager.dto.request.ManagerReviewSearchCondDTO;
import com.kernel.common.manager.dto.response.ManagerReviewRspDTO;
import com.kernel.common.manager.dto.response.ManagerReviewSummaryRspDTO;
import com.kernel.common.manager.service.ManagerReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/managers/reviews")
@RequiredArgsConstructor
public class ManagerReviewController {

    public final ManagerReviewService managerReviewService;

    /**
     * 매니저 리뷰 목록 조회 (검색 조건 및 페이징 처리)
     * @param manager 매니저
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 검색 조건에 따른 매니저 리뷰 목록을 응답 (페이징 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ManagerReviewSummaryRspDTO>>> searchManagerReviews(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @ModelAttribute ManagerReviewSearchCondDTO searchCondDTO,
        Pageable pageable
    ) {
        if (!UserStatus.ACTIVE.equals(manager.getStatus())) { // 활성
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")" );
        }
        Page<ManagerReviewSummaryRspDTO> summaryRspDTOPage
            = managerReviewService.searchManagerReviewsWithPaging(manager.getUserId(), searchCondDTO, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 리뷰 목록 조회 성공", summaryRspDTOPage));
    }

    /**
     * 매니저 리뷰 등록 API
     * @param manager 매니저
     * @param reservationId 예약ID
     * @param requestDTO 매니저 리뷰 등록 요청 데이터
     * @return 작성된 리뷰 정보를 담은 응답
     */
    @PostMapping("/{reservation-id}")
    public ResponseEntity<ApiResponse<ManagerReviewRspDTO>> createManagerReview(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @PathVariable("reservation-id") Long reservationId,
        @Valid @RequestBody ManagerReviewReqDTO requestDTO
    ) {
        if (!UserStatus.ACTIVE.equals(manager.getStatus())) { // 활성
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")" );
        }
        ManagerReviewRspDTO responseDTO = managerReviewService.createManagerReview(manager.getUserId(), reservationId, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 리뷰 등록 성공", responseDTO));
    }
}
