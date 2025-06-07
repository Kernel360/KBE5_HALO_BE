package com.kernel.common.admin.dto.request;

import com.kernel.common.customer.entity.InquiryCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminInquirySearchReqDTO {

    private String authorName;

    private String title;

    private String category;
}
