package com.kernel.reservation.service.response;


import java.time.LocalTime;

import com.kernel.reservation.service.info.ManagerReservationSummaryInfo;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "매니저 예약 요약 응답 DTO")
public class ManagerReservationSummaryRspDTO {

    @Schema(description = "예약 ID", example = "123", required = true)
    private Long reservationId;

    @Schema(description = "청소 요청 날짜", example = "2023-01-01", required = true)
    private LocalDate requestDate;

    @Schema(description = "서비스 이름", example = "일반 청소", required = true)
    private String serviceName;

    @Schema(description = "예약 시간", example = "14:00", required = true)
    private LocalTime startTime;

    @Schema(description = "소요 시간(분)", example = "120", required = true)
    private Integer turnaround;

    @Schema(description = "고객 ID", example = "456", required = true)
    private Long userId;

    @Schema(description = "고객 이름", example = "홍길동", required = true)
    private String userName;

    @Schema(description = "고객 도로명 주소", example = "서울특별시 강남구 테헤란로 123", required = true)
    private String customerRoadAddress;

    @Schema(description = "고객 상세 주소", example = "101동 202호", required = true)
    private String customerDetailAddress;

    @Schema(description = "예약 상태", example = "CONFIRMED", required = true)
    private ReservationStatus status;

    @Schema(description = "체크 ID", example = "789", required = false)
    private Long reservationCheckId;

    @Schema(description = "체크인 여부", example = "true", required = false)
    private Boolean isCheckedIn;

    @Schema(description = "체크인 일시", example = "2023-01-01T14:00:00", required = false)
    private LocalDateTime inTime;

    @Schema(description = "체크아웃 여부", example = "false", required = false)
    private Boolean isCheckedOut;

    @Schema(description = "체크아웃 일시", example = "2023-01-01T16:00:00", required = false)
    private LocalDateTime outTime;

    @Schema(description = "ManagerReservationSummaryInfo에서 필요한 필드만 포함하여 DTO로 변환")
    public static Page<ManagerReservationSummaryRspDTO> fromInfo(Page<ManagerReservationSummaryInfo> info) {
        return info.map(reservation -> ManagerReservationSummaryRspDTO.builder()
                .reservationId(reservation.getReservationId())
                .requestDate(reservation.getRequestDate())
                .serviceName(reservation.getServiceName())
                .startTime(reservation.getStartTime())
                .turnaround(reservation.getTurnaround())
                .userId(reservation.getUserId())
                .userName(reservation.getUserName())
                .customerRoadAddress(reservation.getRoadAddress())
                .customerDetailAddress(reservation.getDetailAddress())
                .status(reservation.getStatus())
                .reservationCheckId(reservation.getServiceCheckId())
                .isCheckedIn(reservation.getIsCheckedIn())
                .inTime(reservation.getInTime() != null ? reservation.getInTime().toLocalDateTime() : null)
                .isCheckedOut(reservation.getIsCheckedOut())
                .outTime(reservation.getOutTime() != null ? reservation.getOutTime().toLocalDateTime() : null)
                .build());
    }
}
