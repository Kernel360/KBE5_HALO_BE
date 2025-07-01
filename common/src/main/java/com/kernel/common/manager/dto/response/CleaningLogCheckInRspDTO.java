package com.kernel.common.manager.dto.response;

import com.kernel.common.reservation.enums.ReservationStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CleaningLogCheckInRspDTO {

    // 체크ID
    private Long checkId;

    // 예약ID
    private Long reservationId;

    // 체크인 일시
    private LocalDateTime inTime;

    // 체크인 첨부파일
    private Long inFileId;

    // 예약 상태
    private ReservationStatus status;

    // 예약 상태명
    private String statusName;
}
