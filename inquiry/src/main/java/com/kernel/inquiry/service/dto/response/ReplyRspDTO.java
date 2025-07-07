package com.kernel.inquiry.service.dto.response;

import com.kernel.inquiry.domain.entity.Reply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReplyRspDTO {

    @Schema(description = "답변 내용", example = "답변 내용 예시", required = false)
    private String content;

    @Schema(description = "답변 첨부파일 ID", example = "456", required = false)
    private Long fileId;

    @Schema(description = "답변 작성일시", example = "2023-01-01T12:00:00", required = false)
    private LocalDateTime createdAt;

    public static ReplyRspDTO fromEntity(Reply reply) {
        return ReplyRspDTO.builder()
                .content(reply.getContent())
                .fileId(reply.getFileId())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}
