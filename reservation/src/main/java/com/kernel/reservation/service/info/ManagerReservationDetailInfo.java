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
public class ManagerReservationDetailInfo {

    private Long reservationId;
    private LocalDate requestDate;
    private LocalTime startTime;
    private Integer turnaround;
    private String serviceName;
    private ReservationStatus status;
    private Long customerId;
    private String extraService;
    private String memo;
    private Long reservationCheckId;
    private Timestamp inTime;
    private Long inFileId;
    private Timestamp outTime;
    private Long outFileId;

}
