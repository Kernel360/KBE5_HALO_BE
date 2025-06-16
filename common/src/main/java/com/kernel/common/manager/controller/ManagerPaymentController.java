package com.kernel.common.manager.controller;

import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.manager.dto.response.ManagerPaymentRspDTO;
import com.kernel.common.manager.service.ManagerPaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/managers/payments")
public class ManagerPaymentController {

    private final ManagerPaymentService paymentService;

    /**
     * 매니저 주급 정산 조회 API
     * @param manager 매니저
     * @param searchYear 조회년도
     * @param searchMonth 조회월
     * @param searchWeekIndexInMonth 조회주차수
     * @return 매니저 주급 정산 정보를 담은 응답
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ManagerPaymentRspDTO>>> getManagerPayments(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @RequestParam("searchYear") Integer searchYear,
        @RequestParam("searchMonth") Integer searchMonth,
        @RequestParam("searchWeekIndexInMonth") Integer searchWeekIndexInMonth
    ) {
        List<ManagerPaymentRspDTO> managerPayments
            = paymentService.getManagerPayments(manager.getUserId(), searchYear, searchMonth, searchWeekIndexInMonth);

        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 주급 정산 조회 성공", managerPayments));
    }
}
