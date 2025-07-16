package com.kernel.settlement.repository;

import com.kernel.settlement.service.info.ManagerSettlementSummaryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomManagerSettleRepository {

    // 관리자 주급정산 조회
    Page<ManagerSettlementSummaryInfo> getSettlementWithPaging(List<Long> reservationId, Pageable pageable, Long userId);
}
