package com.kernel.reservation.domain.entity;

import com.kernel.reservation.domain.enumerate.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {
    private Long reservationId;
//    private Customer customer;
//    private Manager manager;
    private Long serviceId;
    private String phone;
    private String roadAddress;
    private String detailAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDate requestDate;
    private LocalTime startTime;
    private Integer turnaround;
    private ReservationStatus status;
    private Integer price;
    private String memo;

}
