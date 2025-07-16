package com.kernel.settlement.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Schema(description = "관리자 정산 조회 조건 DTO")
public class AdminSettlementSearchCond {

    @Schema(description = "정산 시작 일시", example = "2025-07-14", required = false)
    private LocalDate startDate;

    @Schema(description = "정산 마감 일시", example = "2025-07-14", required = false)
    private LocalDate endDate;

    @Schema(description = "매니저이름", example = "김청소", required = false)
    private String managerName;
}
