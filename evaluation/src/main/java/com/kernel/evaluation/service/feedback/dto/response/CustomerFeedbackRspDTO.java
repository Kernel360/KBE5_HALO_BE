package com.kernel.evaluation.service.feedback.dto.response;

import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.domain.info.CustomerFeedbackInfo;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@Schema(description = "고객 피드백 응답 DTO")
public class CustomerFeedbackRspDTO {

    @Schema(description = "피드백 ID", example = "1")
    private Long feedbackId;

    @Schema(description = "피드백 타입", example = "POSITIVE")
    private FeedbackType feedbackType;

    @Schema(description = "매니저 ID", example = "123")
    private Long managerId;

    @Schema(description = "매니저 이름", example = "홍길동")
    private String managerName;

    @Schema(description = "매니저 평균 별점", example = "4.5")
    private BigDecimal averageRating;

    @Schema(description = "매니저 리뷰 수", example = "10")
    private Integer reviewCount;

    @Schema(description = "최근 예약 날짜", example = "2023-01-01")
    private LocalDate requestDate;

    public static CustomerFeedbackRspDTO fromInfo(CustomerFeedbackInfo info) {
        return CustomerFeedbackRspDTO.builder()
                .feedbackId(info.getFeedbackId())
                .feedbackType(info.getType())
                .managerId(info.getUserId())
                .managerName(info.getUserName())
                .build();
    }
}