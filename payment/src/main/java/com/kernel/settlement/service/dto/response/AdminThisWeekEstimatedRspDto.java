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
@Schema(description = "관리자 이번주 예상 정산 응답 DTO")
public class AdminThisWeekEstimatedRspDto {

    @Schema(description = "이번주 예상 금액", example = "500000", required = true)
    private Long thisWeekEstimated;

    @Schema(description = "이번주 예상 수수료", example = "500000", required = true)
    private Long thisWeekEstimatedPlatformFee;

    public static AdminThisWeekEstimatedRspDto fromInfo(Long price, Long platformFee) {
        return AdminThisWeekEstimatedRspDto.builder()
                .thisWeekEstimated(price)
                .thisWeekEstimatedPlatformFee(platformFee)
                .build();
    }
}
