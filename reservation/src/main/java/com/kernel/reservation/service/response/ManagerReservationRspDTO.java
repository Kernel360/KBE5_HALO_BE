package com.kernel.reservation.service.response;

import com.kernel.reservation.service.info.ManagerReservationDetailInfo;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Schema(description = "매니저 예약 응답 DTO")
public class ManagerReservationRspDTO {

    @Schema(description = "예약 ID", example = "123", required = true)
    private Long reservationId;

    @Schema(description = "청소 요청 날짜", example = "2023-01-01", required = true)
    private LocalDate requestDate;

    @Schema(description = "예약 시간", example = "14:00", required = true)
    private LocalTime startTime;

    @Schema(description = "소요 시간(분)", example = "120", required = true)
    private Integer turnaround;

    @Schema(description = "서비스명", example = "프리미엄 청소 서비스", required = true)
    private String serviceName;

    @Schema(description = "예약 상태", example = "CONFIRMED", required = true)
    private ReservationStatus status;

    @Schema(description = "고객 ID", example = "456", required = true)
    private Long customerId;

    @Schema(description = "추가 서비스명", example = "창문 청소", required = false)
    private String extraServiceName;

    @Schema(description = "고객 요청사항", example = "창문 청소를 꼼꼼히 해주세요.", required = false)
    private String memo;

    @Schema(description = "체크 ID", example = "789", required = false)
    private Long checkId;

    @Schema(description = "체크인 일시", example = "2023-01-01T14:00:00", required = false)
    private LocalDateTime inTime;

    @Schema(description = "체크인 첨부파일 ID", example = "101", required = false)
    private Long inFileId;

    @Schema(description = "체크아웃 일시", example = "2023-01-01T16:00:00", required = false)
    private LocalDateTime outTime;

    @Schema(description = "체크아웃 첨부파일 ID", example = "102", required = false)
    private Long outFileId;

    @Schema(description = "ManagerReservationDetailInfo에서 필요한 필드만 포함하여 DTO로 변환")
    public static ManagerReservationRspDTO fromInfo(ManagerReservationDetailInfo info) {
        return ManagerReservationRspDTO.builder()
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