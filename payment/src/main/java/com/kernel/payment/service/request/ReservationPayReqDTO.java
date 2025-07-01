package com.kernel.payment.service.request;

import com.kernel.payment.common.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationPayReqDTO {

    // 결제 수단
    @NotNull(message = "결제 수단을 선택해주세요.")
    private PaymentMethod paymentMethod;

    // 결제 가격
    @NotNull(message = "서비스를 선택해주세요.")
    private Integer amount;
}
