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
@Schema(description = "관리자 수동 정산 응답 DTO")
public class SettlementCreateRspDTO {

    @Schema(description = "정산완료개수", example = "123", required = true)
    private int createdCount;
}
