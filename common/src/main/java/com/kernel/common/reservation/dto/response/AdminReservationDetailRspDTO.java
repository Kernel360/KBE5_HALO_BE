package com.kernel.common.reservation.dto.response;

import com.kernel.common.reservation.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
public class AdminReservationDetailRspDTO {

    private final Long reservationId;
    private final LocalDate requestDate;
    private final LocalTime startTime;
    private final String roadAddress;
    private final String detailAddress;
    private final String managerName;
    private final String managerPhone;
    private final String customerName;
    private final String customerPhone;
    private final ReservationStatus status;
    private final String serviceName;
    private final Integer price;
    private final String memo;
    private final String cancelReason;
}
