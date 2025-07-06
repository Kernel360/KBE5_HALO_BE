package com.kernel.reservation.service.response;


import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.kernel.reservation.domain.entity.ServiceCheckLog;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServiceCheckOutRspDTO {

    // 예약ID
    private Long reservationId;

    // 체크아웃 일시
    private Timestamp outTime;

    // 체크아웃 첨부파일
    private Long outFileId;

    // 예약 상태
    private ReservationStatus status;

    // entity -> DTO
    public static ServiceCheckOutRspDTO toDTO(ServiceCheckLog entity) {
        return ServiceCheckOutRspDTO.builder()
                .reservationId(entity.getReservation().getReservationId())
                .outTime(entity.getOutTime())
                .outFileId(entity.getOutFileId())
                .status(entity.getReservation().getStatus())
                .build();
    }
}
