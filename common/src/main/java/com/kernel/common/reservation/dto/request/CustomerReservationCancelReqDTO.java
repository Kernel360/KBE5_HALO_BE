package com.kernel.common.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CustomerReservationCancelReqDTO {

    // 예약 ID
    @NotBlank(message = "예약을 선택해주세요.")
    private Long reservationId;

    // 예약 취소 사유
    @NotBlank(message = "취소 사유을 작성해주세요.")
    @Size(min = 5, max = 50, message = "최소 5글자, 최대 50글자까지 작성해주세요.")
    private String cancelReason;
}
