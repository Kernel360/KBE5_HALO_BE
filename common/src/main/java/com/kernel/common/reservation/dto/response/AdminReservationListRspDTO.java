package com.kernel.common.reservation.dto.response;

import com.kernel.common.reservation.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
public class AdminReservationListRspDTO {

    private final Long reservationId;
    private final LocalDate requestDate;
    private final LocalTime startTime;
    private final String roadAddress;
    private final String detailAddress;
    private final String managerName;
    private final String customerName;
    private final ReservationStatus status;
    private final String serviceName;
}
