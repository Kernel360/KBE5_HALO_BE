package com.kernel.inquiry.domain.info;

import com.kernel.inquiry.domain.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReplyInfo {
    private Long inquiryId;
    private Long authorId;
    private String content;
    private Long fileId;
    private LocalDateTime createdAt;
    private Long createdBy;

    public static ReplyInfo fromEntity(Reply reply) {
        return ReplyInfo.builder()
                .inquiryId(reply.getInquiryId().getInquiryId())
                .authorId(reply.getAuthorId())
                .content(reply.getContent())
                .fileId(reply.getFileId())
                .createdAt(reply.getCreatedAt())
                .createdBy(reply.getCreatedBy())
                .build();
    }
}
