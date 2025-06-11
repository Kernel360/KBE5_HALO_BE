package com.kernel.common.admin.dto.response;

import com.kernel.common.customer.entity.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInquiryDetailCustomerRspDTO extends AdminInquiryDetailRspDTO {

    // 고객 이름
    private String userName;

    // 고객 연락처
    private String phone;

    // 고객 이메일
    private String email;

    // 고객 문의사항 유형
    private String categoryName;
}
