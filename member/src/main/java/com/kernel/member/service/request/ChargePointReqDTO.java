package com.kernel.member.service.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChargePointReqDTO {

    @NotNull(message = "충전 금액은 필수입니다.")
    private Integer point;
}
