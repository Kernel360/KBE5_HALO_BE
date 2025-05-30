package com.kernel.common.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInquiryReqDTO {
    // categoryId, title, content

    private Long authorId;
    private String title;
    private String content;


}
