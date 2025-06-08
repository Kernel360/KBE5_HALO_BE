package com.kernel.common.manager.dto.mapper;

import com.kernel.common.manager.dto.request.CleaningLogCheckInReqDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckInRspDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckOutRspDTO;
import com.kernel.common.manager.entity.CleaningLog;
import com.kernel.common.reservation.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class CleaningLogMapper {

    // CheckInRequestDTO -> Entity
    public CleaningLog toCheckInEntity(Reservation reservation, CleaningLogCheckInReqDTO requestDTO) {
        return CleaningLog.builder()
            .reservation(reservation)
            .inFileId(requestDTO.getInFileId())
            .build();

    }

    // Entity -> CheckInResponseDTO
    public CleaningLogCheckInRspDTO toCheckInResponseDTO(CleaningLog cleaningLog) {
        return CleaningLogCheckInRspDTO.builder()
            .checkId(cleaningLog.getCheckId())
            .reservationId(cleaningLog.getReservation().getReservationId())
            .inTime(cleaningLog.getInTime())
            .inFileId(cleaningLog.getInFileId())
            .build();
    }

    // Entity -> CheckOutResponseDTO
    public CleaningLogCheckOutRspDTO toCheckOutResponseDTO(CleaningLog cleaningLog) {
        return CleaningLogCheckOutRspDTO.builder()
            .checkId(cleaningLog.getCheckId())
            .reservationId(cleaningLog.getReservation().getReservationId())
            .outTime(cleaningLog.getOutTime())
            .outFileId(cleaningLog.getOutFileId())
            .status(cleaningLog.getReservation().getStatus())
            .statusName(cleaningLog.getReservation().getStatus().getLabel())
            .build();
    }
}
