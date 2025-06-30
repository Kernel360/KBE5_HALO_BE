package com.kernel.inquiry.service.dto.response;

import com.kernel.inquiry.domain.info.InquiryDetailInfo;
import com.kernel.inquiry.domain.info.ReplyInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@Schema(description = "문의 상세 응답 DTO")
public class InquiryDetailRspDTO extends InquirySummaryRspDTO {

    @Schema(description = "게시글 내용", example = "문의 내용 예시", required = true)
    private String content;

    @Schema(description = "첨부파일 ID", example = "123", required = false)
    private Long fileId;

    @Schema(description = "답변 내용", example = "답변 내용 예시", required = false)
    private String replyContent;

    @Schema(description = "답변 작성일시", example = "2023-01-01T12:00:00", required = false)
    private LocalDateTime replyCreatedAt;

    @Schema(description = "답변 첨부파일 ID", example = "456", required = false)
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