package com.kernel.settlement.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(description = "매니저 정산 응답 DTO")
public class ManagerSettlementSumRspDTO {

    @Schema(description = "이번주 예상 정산 금액", example = "500000", required = true)
    private Long thisWeekEstimated;

    @Schema(description = "저번주 정산 금액", example = "300000", required = true)
    private Long lastWeekSettled;

    @Schema(description = "이번달 정산 금액", example = "1000000", required = true)
    private Long thisMonthSettled;

    public static ManagerSettlementSumRspDTO fromInfo(Long thisWeek, Long lastWeek, Long thisMonth) {
        return ManagerSettlementSumRspDTO.builder()
                .thisWeekEstimated(thisWeek)
                .lastWeekSettled(lastWeek)
                .thisMonthSettled(thisMonth)
                .build();
    }
}
