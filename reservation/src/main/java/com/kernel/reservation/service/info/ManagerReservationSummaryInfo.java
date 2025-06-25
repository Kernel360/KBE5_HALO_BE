package com.kernel.reservation.service.info;

import com.kernel.reservation.domain.entity.ExtraService;
import com.kernel.reservation.domain.entity.ServiceCategory;
import com.kernel.reservation.domain.enums.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerReservationSummaryInfo {

    // 예약 요약 정보
    private Long reservationId;
    private LocalDate requestDate;  // 예약 요청 날짜
    private LocalTime startTime;    // 예약 시작 시간
    private Integer turnaround;     // 예약 소요 시간 (시간 단위)
    private Long customerId;
    private String serviceName;     // 서비스명
    private ReservationStatus status;

    // 체크인 및 체크아웃 정보
    private Long serviceCheckId; // 체크 ID
    private Boolean isCheckedIn; // 체크인 여부
    private Timestamp inTime; // 체크인 일시
    private Boolean isCheckedOut; // 체크아웃 여부
    private Timestamp outTime; // 체크아웃 일시

}
