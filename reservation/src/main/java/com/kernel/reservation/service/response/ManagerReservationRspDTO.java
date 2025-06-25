package com.kernel.reservation.service.response;

import com.kernel.reservation.domain.enums.ReservationStatus;

import com.kernel.reservation.service.info.ManagerReservationDetailInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerReservationRspDTO {

    /* 예약 정보 **************************************/
    // 예약ID
    private Long reservationId;

    // 청소 요청 날짜
    private LocalDate requestDate;

    // 예약시간
    private LocalTime startTime;

    // 소요시간
    private Integer turnaround;

    // 서비스명
    private String serviceName;

    // 상태
    private ReservationStatus status;

    // 상태명
    private String statusName;


    /* 고객 정보 **************************************/
    // 고객 ID
    private Long customerId;


    /* 서비스 상세 ************************************/
    // 추가 서비스
    private String extraServiceName;
    // 고객 요청사항
    private String memo;


    /* 체크인/ 체크아웃 ********************************/
    // 체크ID
    private Long checkId;

    // 체크인 일시
    private LocalDateTime inTime;

    // 체크인 첨부파일
    private Long inFileId;

    // 체크아웃 일시
    private LocalDateTime outTime;

    // 체크아웃 첨부파일
    private Long outFileId;

    // ManagerReservationDetailInfo에서 필요한 필드만 포함
    public static ManagerReservationRspDTO fromInfo(ManagerReservationDetailInfo info) {
        ManagerReservationRspDTO dto = new ManagerReservationRspDTO();
        dto.setReservationId(info.getReservationId());
        dto.setRequestDate(info.getRequestDate());
        dto.setStartTime(info.getStartTime());
        dto.setTurnaround(info.getTurnaround());
        dto.setServiceName(info.getServiceName());
        dto.setStatus(info.getStatus());
        dto.setCustomerId(info.getCustomerId());
        dto.setExtraServiceName(info.getExtraService());
        dto.setMemo(info.getMemo());
        dto.setCheckId(info.getReservationCheckId());
        dto.setInTime(info.getInTime() != null ? info.getInTime().toLocalDateTime() : null);
        dto.setInFileId(info.getInFileId());
        dto.setOutTime(info.getOutTime() != null ? info.getOutTime().toLocalDateTime() : null);
        dto.setOutFileId(info.getOutFileId());

        return dto;
    }

}
