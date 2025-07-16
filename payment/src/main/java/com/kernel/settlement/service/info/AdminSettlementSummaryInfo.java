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
public class AdminSettlementSummaryInfo {

    private Long settlementId;
    private Long reservationId;
    private Long managerId;
    private String managerName;
    private Integer totalAmount;
    private Integer platformFee;
    private SettlementStatus status;
    private LocalDateTime settledAt;
    private String settledBy;
    private String serviceName;
}
