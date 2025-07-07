package com.kernel.reservation.service.info;

import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private LocalDateTime cancelDate;
    private Long canceledById;
    private String cancelReason;
    private Long reservationCheckId;
    private Timestamp inTime;
    private Long inFileId;
    private Timestamp outTime;
    private Long outFileId;
    private String roadAddress;
    private String detailAddress;
    private String userName;
    private String customerReviewContent;
    private Integer customerReviewRating;
    private String managerReviewContent;
    private Integer managerReviewRating;

}
