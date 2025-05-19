package com.kernel360.Halo.web.customer.dto.request;

import lombok.Data;

@Data
public class InquiryRequestDTO {
    // categoryId, title, content

    private Long categoryId;
    private String title;
    private String content;

}
