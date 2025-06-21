package com.kernel.inquiry.service.dto.request;

import com.kernel.inquiry.domain.entity.Inquiry;
import com.kernel.inquiry.domain.entity.Reply;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyCreateReqDTO {

    // 문의 사항 ID
    @NotNull(message = "답변할 문의사항의 ID가 필요합니다.")
    private Long inquiryId;

    // 문의 사항 답변 내용
    @NotBlank(message = "답변 내용을 입력해주세요.")
    private String content;

    // 문의 사항 답변 첨부파일 ID
    private Long fileId;

    // reqDTO to Entity Mapping
    public static Reply toEntity(ReplyCreateReqDTO request, Inquiry inquiry, Long authorId) {
        return Reply.builder()
                .inquiryId(inquiry)
                .authorId(authorId)
                .content(request.getContent())
                .fileId(request.getFileId() != null ? request.getFileId() : null) // 파일 ID가 null인 경우 처리
                .build();
    }
}
