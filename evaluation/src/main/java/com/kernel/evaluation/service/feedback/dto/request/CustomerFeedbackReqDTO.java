package com.kernel.evaluation.service.feedback.dto.request;

import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.domain.entity.Feedback;
import com.kernel.global.domain.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "고객 피드백 요청 DTO")
public class CustomerFeedbackReqDTO {

    @NotNull(message = "매니저를 선택해주세요.")
    @Schema(description = "매니저 ID", example = "123")
    private Long managerId;

    @NotNull(message = "타입을 선택해주세요.")
    @Schema(description = "피드백 타입", example = "POSITIVE")
    private FeedbackType type;

    public Feedback toEntity(Long userId, CustomerFeedbackReqDTO reqDTO) {
        return Feedback.builder()
                .customer(User.builder().userId(userId).build())
                .manager(User.builder().userId(reqDTO.getManagerId()).build())
                .type(reqDTO.getType())
                .build();
    }
}