package com.kernel.inquiry.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class InquiryDeleteReqDTO {

    // 문의사항 ID
    @NotNull(message = "삭제할 문의사항을 선택해주세요.")
    private Long inquiryId;
}
