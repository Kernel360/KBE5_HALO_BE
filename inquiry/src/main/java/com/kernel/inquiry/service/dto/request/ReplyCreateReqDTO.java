package com.kernel.inquiry.service.dto.request;

import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.domain.entity.Reply;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "답변 생성 요청 DTO")
public class ReplyCreateReqDTO {

    @Schema(description = "답변할 문의사항 ID", example = "123", required = true)
    @NotNull(message = "답변할 문의사항의 ID가 필요합니다.")
    private Long inquiryId;

    @Schema(description = "답변 내용", example = "답변 내용 예시", required = true)
    @NotBlank(message = "답변 내용을 입력해주세요.")
    private String content;

    @Schema(description = "답변 첨부파일 ID", example = "456", required = false)
    private Long fileId;

    public Reply toEntity(ReplyCreateReqDTO request, Inquiry inquiry, Long authorId) {
        return Reply.builder()
                .inquiryId(inquiry)
                .authorId(authorId)
                .content(request.getContent())
                .fileId(request.getFileId() != null ? request.getFileId() : null)
                .build();
    }
}