package com.kernel.settlement.controller;

import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.settlement.service.ManagerSettlementService;
import com.kernel.settlement.service.dto.request.ManagerSettlementSearchCond;
import com.kernel.settlement.service.dto.response.ManagerSettlementSumRspDTO;
import com.kernel.settlement.service.dto.response.ManagerSettlementSummaryRspDTO;
import com.kernel.settlement.service.dto.response.ManagerThisWeekEstimatedRspDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/managers/settlements")
@RequiredArgsConstructor
public class ManagerSettlementController {

    private final ManagerSettlementService managerSettlementService;

    /**
     * 정산 조회
     * @param cond 시작 날짜
     * @param pageable 페이지
     * @param user 로그인한 유저
     * @return 조회된 정산 내역
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ManagerSettlementSummaryRspDTO>>> getSettlementWithPaging(
            @ModelAttribute ManagerSettlementSearchCond cond,
            @PageableDefault(size = 10, sort = "settledAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails user
    )
    {
        Page<ManagerSettlementSummaryRspDTO> rspDTO = managerSettlementService.getSettlementWithPaging(cond, pageable, user.getUserId());
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 정산 목록 조회 성공", rspDTO));
    }

    /**
     * 이번주 예상 정산 금액 조회
     * @param user 로그인한 유저
     * @return 조회된 정산 내역
     */
    @GetMapping("/week")
    public ResponseEntity<ApiResponse<ManagerThisWeekEstimatedRspDto>> getCurrentWeekEstimated(
            @AuthenticationPrincipal CustomUserDetails user
    )
    {
        ManagerThisWeekEstimatedRspDto rspDTO = managerSettlementService.getThisWeekEstimated(user.getUserId());
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 이번주 정산 조회 성공", rspDTO));
    }

    /**
     * 정산 요약
     * @param user 로그인한 유저
     * @return 조회된 정산 내역
     */
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<ManagerSettlementSumRspDTO>> getSettlementSummary(
            @AuthenticationPrincipal CustomUserDetails user
    )
    {
        ManagerSettlementSumRspDTO rspDTO = managerSettlementService.getSettlementSummary(user.getUserId());
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 정산 요약 조회 성공", rspDTO));
    }

}
