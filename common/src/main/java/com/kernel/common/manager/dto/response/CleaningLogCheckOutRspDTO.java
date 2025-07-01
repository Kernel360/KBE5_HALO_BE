package com.kernel.common.manager.dto.response;

import com.kernel.common.reservation.enums.ReservationStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CleaningLogCheckOutRspDTO {

    // 체크ID
    private Long checkId;

    // 예약ID
    private Long reservationId;

    // 체크아웃 일시
    private LocalDateTime outTime;

    // 체크아웃 첨부파일
    private Long outFileId;

    // 예약 상태
    private ReservationStatus status;

    // 예약 상태명
    private String statusName;
}
