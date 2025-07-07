package com.kernel.inquiry.service.info;

import com.kernel.inquiry.common.enums.AuthorType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InquiryAdminSummaryInfo {

    private Long inquiryId;
    private String categoryName;
    private String title;
    private LocalDateTime createdAt;
    private Boolean isReplied;
    private AuthorType authorType;
    private String userName;

}
