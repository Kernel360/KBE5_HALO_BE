package com.kernel.settlement.service.info;

import com.kernel.settlement.common.enums.SettlementStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerSettlementSummaryInfo {

    private Long settlementId;
    private Long reservationId;
    private Integer totalAmount;
    private SettlementStatus status;
    private LocalDateTime settledAt;
}
