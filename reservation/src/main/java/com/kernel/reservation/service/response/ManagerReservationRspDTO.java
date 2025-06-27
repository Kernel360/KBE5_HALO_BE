package com.kernel.reservation.service.response;


import com.kernel.reservation.service.info.ManagerReservationDetailInfo;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ManagerReservationRspDTO {

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

    // 고객 ID
    private Long customerId;

    // 추가 서비스
    private String extraServiceName;

    // 고객 요청사항
    private String memo;

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
       return  ManagerReservationRspDTO.builder()
                .reservationId(info.getReservationId())
                .requestDate(info.getRequestDate())
                .startTime(info.getStartTime())
                .turnaround(info.getTurnaround())
                .serviceName(info.getServiceName())
                .status(info.getStatus())
                .customerId(info.getCustomerId())
                .extraServiceName(info.getExtraService())
                .memo(info.getMemo())
                .checkId(info.getReservationCheckId())
                .inTime(info.getInTime() != null ? info.getInTime().toLocalDateTime() : null)
                .inFileId(info.getInFileId())
                .outTime(info.getOutTime() != null ? info.getOutTime().toLocalDateTime() : null)
                .outFileId(info.getOutFileId())
                .build();
    }

}
