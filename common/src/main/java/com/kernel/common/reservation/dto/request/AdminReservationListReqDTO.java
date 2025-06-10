package com.kernel.common.reservation.dto.request;

import com.kernel.common.reservation.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminReservationListReqDTO {

    private final ReservationStatus reservationStatus;
}
