package com.kernel.settlement.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@Schema(description = "관리자 수동 정산 응답 DTO")
@ToString
public class ManagerSettlementSearchCond {

    @Schema(description = "정산 시작 일시", example = "yyyy-MM-dd", required = false)
    private LocalDate startDate;

    @Schema(description = "정산 마감 일시", example = "yyyy-MM-dd", required = false)
    private LocalDate endDate;
}



