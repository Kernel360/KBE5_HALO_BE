package com.kernel.reservation.service.request;

import com.kernel.payment.service.request.ReservationPayReqDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationConfirmReqDTO {

    // 결제 정보
    @Valid
    private ReservationPayReqDTO payReqDTO;

    // 선택한 매니저 ID
    @NotNull(message = "매니저를 선택해주세요.")
    private Long selectedManagerId;

}
