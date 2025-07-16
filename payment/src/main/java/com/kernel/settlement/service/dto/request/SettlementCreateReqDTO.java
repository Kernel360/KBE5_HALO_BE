package com.kernel.settlement.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "관리자 정산 요청 DTO")
@Getter
public class SettlementCreateReqDTO {

    @Schema(description = "정산일시 시작일", example = "2023-01-01", required = false)
    @NotNull(message = "정산 시작 날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "정산일시 종료일", example = "2023-12-31", required = false)
    @NotNull(message = "정산 마감 날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
