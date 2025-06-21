package com.kernel.inquiry.domain.info;

import com.kernel.inquiry.domain.entity.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InquiryDetailInfo {
    private Long inquiryId;
    private Enum<?> category;
    private String title;
    private String content;
    private Long fileId;
    private LocalDateTime createdAt;
    private Boolean isReplied;

    public static InquiryDetailInfo fromEntity(Inquiry inquiry) {
        return InquiryDetailInfo.builder()
                .inquiryId(inquiry.getInquiryId())
                .category(inquiry.getCategory())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .fileId(inquiry.getFileId())
                .createdAt(inquiry.getCreatedAt())
                .isReplied(inquiry.getIsReplied())
                .build();
    }
}
