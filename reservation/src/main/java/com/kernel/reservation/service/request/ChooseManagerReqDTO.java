package com.kernel.reservation.service.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChooseManagerReqDTO {

    // 선택한 매니저 ID
    @NotNull(message = "매니저를 선택해주세요.")
    private Long selectedManagerId;

}
