package com.kernel.evaluation.service.feedback.dto.response;

import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.domain.entity.Feedback;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerFeedbackUpdateRspDTO {

    // 매니저ID
    private Long managerId;

    // 피드백 타입
    private FeedbackType type;

    // 삭제 여부
    private Boolean deleted;

    public static CustomerFeedbackUpdateRspDTO toRspDTO(Feedback feedback) {
        return CustomerFeedbackUpdateRspDTO.builder()
                .managerId(feedback.getManager().getUserId())
                .type(feedback.getType())
                .deleted(feedback.getDeleted())
                .build();
    }
}
