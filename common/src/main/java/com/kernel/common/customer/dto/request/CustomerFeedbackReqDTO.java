package com.kernel.common.customer.dto.request;

import com.kernel.common.global.enums.FeedbackType;
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


}
