package com.kernel.common.admin.dto.response;

import com.kernel.common.customer.entity.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInquiryDetailCustomerRspDTO extends AdminInquiryDetailRspDTO {

    // 고객 정보
    private Customer author;

    // 고객 문의사항 유형
    private String categoryName;
}
