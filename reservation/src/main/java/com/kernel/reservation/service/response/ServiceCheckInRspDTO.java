package com.kernel.reservation.service.response;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.kernel.reservation.domain.entity.ServiceCheckLog;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServiceCheckInRspDTO {
    // 예약ID
    private Long reservationId;

    // 체크인 일시
    private Timestamp inTime;

    // 체크인 첨부파일
    private Long inFileId;

    // 예약 상태
    private ReservationStatus status;

    // entity -> DTO
    public static ServiceCheckInRspDTO toDTO(ServiceCheckLog entity) {
        return ServiceCheckInRspDTO.builder()
                .reservationId(entity.getReservation().getReservationId())
                .inTime(entity.getInTime())
                .inFileId(entity.getInFileId())
                .status(entity.getReservation().getStatus())
                .build();
    }
}
