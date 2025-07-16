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
@Schema(description = "관리자 정산 응답 DTO")
public class AdminSettlementSumRspDTO {

    @Schema(description = "이번주 예상 정산 금액", example = "500000", required = true)
    private Long thisWeekEstimated;

    @Schema(description = "이번주 예상 수수료", example = "500000", required = true)
    private Long thisWeekEstimatedPlatformFee;

    @Schema(description = "저번주 정산 금액", example = "300000", required = true)
    private Long lastWeekSettled;

    @Schema(description = "저번주 수수료", example = "500000", required = true)
    private Long lastWeekSettledPlatformFee;

    @Schema(description = "이번달 정산 금액", example = "1000000", required = true)
    private Long thisMonthSettled;

    @Schema(description = "이번달 수수료", example = "500000", required = true)
    private Long thisMonthSettledPlatformFee;

    public static AdminSettlementSumRspDTO fromInfo(Long thisWeekEstimated, Long thisWeekEstimatedPlatformFee,
                                                    Long lastWeekSettled, Long lastWeekSettledPlatformFee,
                                                    Long thisMonthSettled, Long thisMonthSettledPlatformFee) {
        return AdminSettlementSumRspDTO.builder()
                .thisWeekEstimated(thisWeekEstimated)
                .thisWeekEstimatedPlatformFee(thisWeekEstimatedPlatformFee)
                .lastWeekSettled(lastWeekSettled)
                .lastWeekSettledPlatformFee(lastWeekSettledPlatformFee)
                .thisMonthSettled(thisMonthSettled)
                .thisMonthSettledPlatformFee(thisMonthSettledPlatformFee)
                .build();
    }
}
