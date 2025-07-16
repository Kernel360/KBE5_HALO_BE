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
@Schema(description = "매니저 이번주 예상 정산 응답 DTO")
public class ManagerThisWeekEstimatedRspDto {

    @Schema(description = "이번주 예상 금액", example = "500000", required = true)
    private int thisWeekEstimated;

    public static ManagerThisWeekEstimatedRspDto fromInfo(int result) {
        return ManagerThisWeekEstimatedRspDto.builder()
                .thisWeekEstimated(result)
                .build();
    }
}
