package com.kernel.evaluation.service.feedback.dto.request;

import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.domain.entity.Feedback;
import com.kernel.global.domain.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerFeedbackReqDTO {

    // 매니저ID
    @NotNull(message = "매니저를 선택해주세요.")
    private Long managerId;

    // 피드백 타입
    @NotNull(message = "타입을 선택해주세요.")
    private FeedbackType type;

    public Feedback toEntity(Long userId, CustomerFeedbackReqDTO reqDTO) {
        return Feedback.builder()
                .customer(User.builder().userId(userId).build())
                .manager(User.builder().userId(reqDTO.getManagerId()).build())
                .type(reqDTO.getType())
                .build();
    }
}
