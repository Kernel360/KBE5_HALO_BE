package com.kernel.reservation.service.response;

import com.kernel.reservation.domain.enums.ReservationStatus;

import java.time.LocalTime;

import com.kernel.reservation.service.info.ManagerReservationSummaryInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerReservationSummaryRspDTO {

    // 예약ID
    private Long reservationId;

    // 청소 요청 날짜
    private LocalDate requestDate;

    // 예약시간
    private LocalTime startTime;

    // 소요시간
    private Integer turnaround;

    // 고객 ID
    private Long customerId;

    // 상태
    private ReservationStatus status;

    // 체크 ID
    private Long reservationCheckId;

    // 체크인 여부
    private Boolean isCheckedIn;
    
    // 체크인 일시
    private LocalDateTime inTime;

    // 체크아웃 여부
    private Boolean isCheckedOut;
    
    // 체크아웃 일시
    private LocalDateTime outTime;

    // ManagerReservationSummaryInfo에서 필요한 필드만 포함
    public static Page<ManagerReservationSummaryRspDTO> fromInfo(Page<ManagerReservationSummaryInfo> info) {
        return info.map(reservation -> ManagerReservationSummaryRspDTO.builder()
                .reservationId(reservation.getReservationId())
                .requestDate(reservation.getRequestDate())
                .startTime(reservation.getStartTime())
                .turnaround(reservation.getTurnaround())
                .customerId(reservation.getCustomerId())
                .status(reservation.getStatus())
                .reservationCheckId(reservation.getServiceCheckId())
                .isCheckedIn(reservation.getIsCheckedIn())
                .inTime(reservation.getInTime() != null ? reservation.getInTime().toLocalDateTime() : null)
                .isCheckedOut(reservation.getIsCheckedOut())
                .outTime(reservation.getOutTime() != null ? reservation.getOutTime().toLocalDateTime() : null)
                .build());
    }
}
