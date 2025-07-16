package com.kernel.settlement.service.dto.response;

import com.kernel.settlement.service.info.ManagerSettlementSummaryInfo;
import com.kernel.sharedDomain.service.response.ReservationScheduleInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "매니저 정산 목록 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerSettlementSummaryRspDTO {

    @Schema(description = "정산ID", example = "1", required = true)
    private Long settlementId;

    @Schema(description = "예약ID", example = "1", required = true)
    private Long reservationId;

    @Schema(description = "서비스날짜", example = "2025-01-10", required = true)
    private LocalDate requestDate;

    @Schema(description = "서비스 시작시간", example = "08:00:00", required = true)
    private LocalDateTime startTime;

    @Schema(description = "서비스 소요시간", example = "2", required = true)
    private Integer turnaround;

    @Schema(description = "서비스분류명", example = "대청소", required = true)
    private String serviceName;

    @Schema(description = "정산금액", example = "50000", required = true)
    private Integer totalAmount;

    @Schema(description = "정산상태", example = "SETTLED", required = true)
    private String status;

    @Schema(description = "정산날짜", example = "2025-07-14T02:00:00", required = true)
    private LocalDateTime settledAt;

    //  ManagerSettlementSummaryInfo -> ManagerSettlementSummaryRspDTO
    public static ManagerSettlementSummaryRspDTO fromInfo(ManagerSettlementSummaryInfo info, ReservationScheduleInfo scheduleInfo) {
        return ManagerSettlementSummaryRspDTO.builder()
                .settlementId(info.getSettlementId())
                .reservationId(info.getReservationId())
                .requestDate(scheduleInfo != null ? scheduleInfo.getRequestDate() : null)
                .startTime(scheduleInfo != null ? scheduleInfo.getStartTime().atDate(scheduleInfo.getRequestDate()) : null)
                .turnaround(scheduleInfo != null ? scheduleInfo.getTurnaround() : null)
                .serviceName(scheduleInfo != null ? scheduleInfo.getServiceName() : null)
                .totalAmount(info.getTotalAmount())
                .status(info.getStatus().name())
                .settledAt(info.getSettledAt())
                .build();
    }
}
