package com.kernel.settlement.repository;

import com.kernel.settlement.service.info.AdminSettlementSummaryInfo;
import com.kernel.settlement.service.info.SettledAmountWithPlatformFeeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomAdminSettleRepository {

    // 관리자 주급정산 조회
    Page<AdminSettlementSummaryInfo> getSettlementWithPaging(List<Long> reservationIds, Pageable pageable);

    // 관리자 정산금액 합계 조회
    SettledAmountWithPlatformFeeInfo getSettlementByReservationIds(List<Long> reservationIds);
}
