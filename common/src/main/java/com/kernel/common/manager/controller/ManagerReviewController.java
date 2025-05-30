package com.kernel.common.manager.controller;

import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.manager.dto.request.ManagerReviewReqDTO;
import com.kernel.common.manager.dto.response.ManagerReviewRspDTO;
import com.kernel.common.manager.service.ManagerReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
     * @param reservationId 예약ID
     * @param requestDTO 매니저 리뷰 등록 요청 데이터
     * @return 작성된 리뷰 정보를 담은 응답
     */
    @PostMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<ManagerReviewRspDTO>> createManagerReview(
        @PathVariable("reservation_id") Long reservationId,
        @Valid @RequestBody ManagerReviewReqDTO requestDTO
    ) {
        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        ManagerReviewRspDTO responseDTO = managerReviewService.createManagerReview(1L, reservationId, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 리뷰 등록 성공", responseDTO));
    }
}
