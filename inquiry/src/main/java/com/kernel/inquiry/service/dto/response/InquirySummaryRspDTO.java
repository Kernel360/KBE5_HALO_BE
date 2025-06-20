package com.kernel.inquiry.service.dto.response;

import com.kernel.inquiry.domain.info.InquirySummaryInfo;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class InquirySummaryRspDTO {

    // 게시글 ID
    private Long inquiryId;

    // 문의 유형 이름
    private Enum<?> category;

    // 제목
    private String title;

    // 작성일시
    private LocalDateTime createdAt;

    // 답변 여부
    private Boolean isReplied;

    public static Page<InquirySummaryRspDTO> fromInfo(Page<InquirySummaryInfo> inquirySummaryInfo) {
        return inquirySummaryInfo.map(info -> InquirySummaryRspDTO.builder()
                .inquiryId(info.getInquiryId())
                .category(info.getCategory())
                .title(info.getTitle())
                .createdAt(info.getCreatedAt())
                .isReplied(info.getIsReplied())
                .build());
    }

}
