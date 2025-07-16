package com.kernel.settlement.service;

import com.kernel.settlement.service.dto.request.ManagerSettlementSearchCond;
import com.kernel.settlement.service.dto.response.ManagerSettlementSumRspDTO;
import com.kernel.settlement.service.dto.response.ManagerSettlementSummaryRspDTO;
import com.kernel.settlement.service.dto.response.ManagerThisWeekEstimatedRspDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerSettlementService {

    // 매니저 정산 조회
    Page<ManagerSettlementSummaryRspDTO> getSettlementWithPaging(ManagerSettlementSearchCond cond, Pageable pageable, Long userId);

    // 매니저 이번주 예상 정산 금액 조회
    ManagerThisWeekEstimatedRspDto getThisWeekEstimated(Long userId);

    // 정산 요약 조회(이번주 예상정산, 저번주 정산금액, 이번달 정산금액)
    ManagerSettlementSumRspDTO getSettlementSummary(Long userId);
}
