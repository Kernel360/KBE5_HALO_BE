package com.kernel.common.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInquiryReplyReqDTO {

    // 문의 사항 ID
    @NotNull(message = "답변할 문의사항의 ID가 필요합니다.")
    private Long inquiryId;

    // 문의 사항 답변 내용
    @NotBlank(message = "답변 내용을 입력해주세요.")
    private String content;

    // 문의 사항 답변 첨부파일 ID
    private Long fileId;
}
