package com.kernel.reservation.domain.entity;

import java.sql.Timestamp;

public class CleaningLog {
    private Long reservationId;
    private Timestamp inTime;
    private Long inFileId;
    private Timestamp outTime;
    private Long outFileId;
}
