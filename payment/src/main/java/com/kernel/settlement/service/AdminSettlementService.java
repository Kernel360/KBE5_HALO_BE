package com.kernel.settlement.service;

import com.kernel.settlement.service.dto.request.AdminSettlementSearchCond;
import com.kernel.settlement.service.dto.response.AdminSettlementSumRspDTO;
import com.kernel.settlement.service.dto.response.AdminSettlementSummaryRspDTO;
import com.kernel.settlement.service.dto.response.AdminThisWeekEstimatedRspDto;
import com.kernel.settlement.service.dto.response.SettlementCreateRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AdminSettlementService {

    // 주급 정산 생성
    SettlementCreateRspDTO createWeeklySettlement(LocalDate start, LocalDate end, Long userId);

    // 주급 정산 조회
    Page<AdminSettlementSummaryRspDTO> getSettlementWithPaging(AdminSettlementSearchCond cond, Pageable pageable);

    // 이번주 예상 정산 금액 조회
    AdminThisWeekEstimatedRspDto getThisWeekEstimated();

    // 저번주, 이번달 정산 금액
    AdminSettlementSumRspDTO getSettlementSummary();
}
