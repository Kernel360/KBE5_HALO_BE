package com.kernel.inquiry.service.dto.response;

import com.kernel.inquiry.service.info.ReplyAdminDetailInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReplyAdminDetailRspDTO {

    @Schema(description = "답변ID", example = "1", required = false)
    private Long replyId;

    @Schema(description = "답변 작성자 이름", example = "관리자", required = false)
    private String userName;

    @Schema(description = "답변 내용", example = "답변합니다.", required = false)
    private String content;

    @Schema(description = "답변 첨부파일 ID", example = "123", required = false)
    private Long fileId;

    @Schema(description = "답변 작성 일시", example = "2023-01-01T12:00:00", required = false)
    private LocalDateTime createdAt;

    public static ReplyAdminDetailRspDTO fromInfo(ReplyAdminDetailInfo detailInfo) {
        return ReplyAdminDetailRspDTO.builder()
                .replyId(detailInfo.getReplyId())
                .userName(detailInfo.getUserName())
                .content(detailInfo.getContent())
                .fileId(detailInfo.getFileId())
                .createdAt(detailInfo.getCreatedAt())
                .build();
    }
}
