package com.kernel.inquiry.service.dto.response;

import com.kernel.inquiry.domain.info.InquiryDetailInfo;
import com.kernel.inquiry.domain.info.ReplyInfo;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class InquiryDetailRspDTO extends InquirySummaryRspDTO {

    // 게시글 내용
    private String content;

    // 첨부파일 ID
    private Long fileId;

    // 답변 내용
    private String replyContent;

    // 답변 작성일시
    private LocalDateTime replyCreatedAt;

    // 답변 첨부파일 ID
    private Long replyFileId;

    public static InquiryDetailRspDTO fromInfo(InquiryDetailInfo inquiryDetailInfo, ReplyInfo replyInfo) {
        return InquiryDetailRspDTO.builder()
                .inquiryId(inquiryDetailInfo.getInquiryId())
                .category(inquiryDetailInfo.getCategory())
                .title(inquiryDetailInfo.getTitle())
                .content(inquiryDetailInfo.getContent())
                .createdAt(inquiryDetailInfo.getCreatedAt())
                .isReplied(inquiryDetailInfo.getIsReplied())
                .fileId(inquiryDetailInfo.getFileId())
                .replyContent(replyInfo.getContent())
                .replyCreatedAt(replyInfo.getCreatedAt())
                .replyFileId(replyInfo.getFileId())
                .build();
    }
}
