package com.kernel.common.manager.controller;

import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.security.ManagerUserDetails;
import com.kernel.common.manager.dto.request.ManagerReviewReqDTO;
import com.kernel.common.manager.dto.response.ManagerReviewRspDTO;
import com.kernel.common.manager.service.ManagerReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
     * 매니저 리뷰 등록 API
     * @param manager 매니저
     * @param reservationId 예약ID
     * @param requestDTO 매니저 리뷰 등록 요청 데이터
     * @return 작성된 리뷰 정보를 담은 응답
     */
    @PostMapping("/{reservation-id}")
    public ResponseEntity<ApiResponse<ManagerReviewRspDTO>> createManagerReview(
        @AuthenticationPrincipal ManagerUserDetails manager,
        @PathVariable("reservation-id") Long reservationId,
        @Valid @RequestBody ManagerReviewReqDTO requestDTO
    ) {
        ManagerReviewRspDTO responseDTO = managerReviewService.createManagerReview(manager.getManagerId(), reservationId, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 리뷰 등록 성공", responseDTO));
    }
}
