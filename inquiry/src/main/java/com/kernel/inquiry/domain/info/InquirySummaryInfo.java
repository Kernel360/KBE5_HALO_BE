package com.kernel.inquiry.domain.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InquirySummaryInfo {

    private Long inquiryId;
    private Enum<?> category;
    private String title;
    private LocalDateTime createdAt;
    private Boolean isReplied;
}
