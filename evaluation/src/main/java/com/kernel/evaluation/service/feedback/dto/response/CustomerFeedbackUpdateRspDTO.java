package com.kernel.evaluation.service.feedback.dto.response;

import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.domain.entity.Feedback;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Builder
@Schema(description = "고객 피드백 업데이트 응답 DTO")
public class CustomerFeedbackUpdateRspDTO {

    @Schema(description = "매니저 ID", example = "123")
    private Long managerId;

    @Schema(description = "피드백 타입", example = "POSITIVE")
    private FeedbackType type;

    @Schema(description = "삭제 여부", example = "true")
    private Boolean deleted;

    public static CustomerFeedbackUpdateRspDTO fromInfo(Feedback feedback) {
        return CustomerFeedbackUpdateRspDTO.builder()
                .managerId(feedback.getManager().getUserId())
                .type(feedback.getType())
                .deleted(feedback.getDeleted())
                .build();
    }
}