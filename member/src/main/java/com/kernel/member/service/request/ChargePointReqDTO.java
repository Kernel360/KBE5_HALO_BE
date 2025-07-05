package com.kernel.member.service.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChargePointReqDTO {

    @NotNull(message = "충전 금액은 필수입니다.")
    @Schema(description = "충전 금액", example = "1000")
    private Integer point;
}
