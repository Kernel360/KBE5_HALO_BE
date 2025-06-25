package com.kernel.reservation.service.info;

import com.kernel.reservation.domain.enums.ReservationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerReservationDetailInfo {

    // 예약 상세 정보
    private Long reservationId;
    private LocalDate requestDate;  // 예약 요청 일자
    private LocalTime startTime;
    private Integer turnaround;     // 예약 소요 시간 (시간 단위)
    private String serviceName;
    private ReservationStatus status;
    private Long customerId;
    private String extraService;
    private String memo;

    // 체크인 여부
    private Long reservationCheckId; // 체크 ID
    private Timestamp inTime; // 체크인 여부
    private Long inFileId; // 체크인 첨부파일
    private Timestamp outTime; // 체크아웃 여부
    private Long outFileId; // 체크아웃 첨부파일

}
