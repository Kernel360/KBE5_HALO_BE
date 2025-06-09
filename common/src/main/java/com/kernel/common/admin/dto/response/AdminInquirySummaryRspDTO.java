package com.kernel.common.admin.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminInquirySummaryRspDTO {
    private Long inquiryId;

    private Long authorId;

    private String title;

    private LocalDateTime createdAt;
}
