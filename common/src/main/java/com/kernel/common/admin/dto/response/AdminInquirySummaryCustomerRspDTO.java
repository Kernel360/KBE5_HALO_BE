package com.kernel.common.admin.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInquirySummaryCustomerRspDTO extends AdminInquirySummaryRspDTO {
    // 문의사항 카테고리 이름
    private String categoryName;
}
