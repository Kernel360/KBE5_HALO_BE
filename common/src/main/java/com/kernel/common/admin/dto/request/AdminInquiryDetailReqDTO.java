package com.kernel.common.admin.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInquiryDetailReqDTO {

    // 작성자 ID
    @NotNull
    private Long authorId;
}
