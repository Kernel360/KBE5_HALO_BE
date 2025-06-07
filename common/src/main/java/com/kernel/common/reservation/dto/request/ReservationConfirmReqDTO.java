package com.kernel.common.reservation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class ReservationConfirmReqDTO {

    // 선택한 매니저 ID
    @NotNull(message = "매니저를 선택해주세요.")
    private Long selectedManagerId;

    // 매칭 매니저 리스트 ID
    private List<Long> matchedManagerIds;
}
