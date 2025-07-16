package com.kernel.settlement.service.dto.response;

import com.kernel.settlement.service.info.AdminSettlementSummaryInfo;
import com.kernel.sharedDomain.service.response.ScheduleAndMatchInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Schema(description = "관리자 정산 목록 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminSettlementSummaryRspDTO {

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

    @Schema(description = "매니저ID", example = "1", required = true)
    private Long managerId;

    @Schema(description = "매니저이름", example = "김청소", required = true)
    private String managerName;

    @Schema(description = "정산금액", example = "50000", required = true)
    private Integer totalAmount;

    @Schema(description = "수수료", example = "20000", required = true)
    private Integer platformFee;

    @Schema(description = "정산상태", example = "SETTLED", required = true)
    private String status;

    @Schema(description = "정산날짜", example = "2025-07-14T02:00:00", required = true)
    private LocalDateTime settledAt;

    @Schema(description = "정산자 이름", example = "관리자2", required = true)
    private String settledBy;

    // AdminSettlementSummaryInfo -> AdminSettlementSummaryRspDTO
    public static AdminSettlementSummaryRspDTO fromInfo(AdminSettlementSummaryInfo info, ScheduleAndMatchInfo scheduleInfo) {
        return AdminSettlementSummaryRspDTO.builder()
                .settlementId(info.getSettlementId())
                .reservationId(info.getReservationId())
                .requestDate(scheduleInfo != null ? scheduleInfo.getRequestDate() : null)
                .startTime(scheduleInfo != null ? scheduleInfo.getStartTime().atDate(scheduleInfo.getRequestDate()) : null)
                .turnaround(scheduleInfo != null ? scheduleInfo.getTurnaround() : null)
                .serviceName(info.getServiceName())
                .managerId(info.getManagerId())
                .managerName(info.getManagerName())
                .totalAmount(info.getTotalAmount())
                .platformFee(info.getPlatformFee())
                .status(info.getStatus().name())
                .settledAt(info.getSettledAt())
                .settledBy(info.getSettledBy())
                .build();
    }

}
