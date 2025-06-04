package com.kernel.common.customer.dto.response;

import com.kernel.common.global.enums.FeedbackType;
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
}
