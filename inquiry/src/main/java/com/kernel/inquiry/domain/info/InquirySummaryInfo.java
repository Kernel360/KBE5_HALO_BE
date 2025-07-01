package com.kernel.inquiry.domain.info;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InquirySummaryInfo {

    private Long inquiryId;
    private Enum<?> category;
    private String title;
    private LocalDateTime createdAt;
    private Boolean isReplied;
}
