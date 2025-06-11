package com.kernel.common.admin.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AdminInquirySummaryManagerRspDTO {
    private Long inquiryId;

    private Long authorId;

    private String title;

    private LocalDateTime createdAt;

    private Boolean replyStatus;
}
