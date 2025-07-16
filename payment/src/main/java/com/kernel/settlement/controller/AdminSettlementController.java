package com.kernel.settlement.controller;

import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.settlement.service.AdminSettlementService;
import com.kernel.settlement.service.dto.request.AdminSettlementSearchCond;
import com.kernel.settlement.service.dto.request.SettlementCreateReqDTO;
import com.kernel.settlement.service.dto.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/settlements")
@RequiredArgsConstructor
public class AdminSettlementController {

    private final AdminSettlementService adminSettlementService;

    /**
     * 수동 주급 정산
     * @param reqDTO 시작 날짜
     * @param user 로그인 유저 ID
     * @return 정산 완료된 개수
     */
    @PostMapping
    public ResponseEntity<ApiResponse<SettlementCreateRspDTO>> createSettlementByAdmin(
            @RequestBody @Valid SettlementCreateReqDTO reqDTO,
            @AuthenticationPrincipal CustomUserDetails user
    )
    {
        SettlementCreateRspDTO rspDTO = adminSettlementService.createWeeklySettlement(reqDTO.getStartDate(), reqDTO.getEndDate(), user.getUserId());

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 수동 정산 성공", rspDTO));
    }

    /**
     * 정산 조회
     * @param cond 시작 날짜
     * @return 정산 완료된 개수
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminSettlementSummaryRspDTO>>> getSettlementWithPaging(
            @ModelAttribute AdminSettlementSearchCond cond,
            @PageableDefault(size = 10, page = 0, sort = "settledAt", direction = Sort.Direction.DESC) Pageable pageable
    )
    {
        Page<AdminSettlementSummaryRspDTO> rspDTO = adminSettlementService.getSettlementWithPaging(cond, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 정산 조회 성공", rspDTO));
    }

    /**
     * 관리자 이번주 예상 정산 금액 조회
     * @return 조회된 정산 내역
     */
    @GetMapping("/week")
    public ResponseEntity<ApiResponse<AdminThisWeekEstimatedRspDto>> getCurrentWeekEstimated(
    )
    {
        AdminThisWeekEstimatedRspDto rspDTO = adminSettlementService.getThisWeekEstimated();
        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 이번주 정산 조회 성공", rspDTO));
    }

    /**
     * 관리자 저번주, 이번달 정산 금액 요약
     * @return 조회된 정산 내역
     */
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<AdminSettlementSumRspDTO>> getSettlementSummary(
    )
    {
        AdminSettlementSumRspDTO rspDTO = adminSettlementService.getSettlementSummary();
        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 정산 요약 조회 성공", rspDTO));
    }
}
