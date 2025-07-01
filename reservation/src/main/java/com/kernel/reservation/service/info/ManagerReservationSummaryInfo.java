package com.kernel.reservation.service.info;

import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerReservationSummaryInfo {

    private Long reservationId;
    private LocalDate requestDate;
    private LocalTime startTime;
    private Integer turnaround;
    private Long customerId;
    private String serviceName;
    private ReservationStatus status;
    private Long serviceCheckId;
    private Boolean isCheckedIn;
    private Timestamp inTime;
    private Boolean isCheckedOut;
    private Timestamp outTime;

}
