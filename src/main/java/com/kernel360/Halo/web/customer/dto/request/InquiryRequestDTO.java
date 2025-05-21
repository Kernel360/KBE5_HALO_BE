package com.kernel360.Halo.web.customer.dto.request;

import lombok.Data;

@Data
public class InquiryRequestDTO {
    // categoryId, title, content, deleted

    private Long authorId;
    private String title;
    private String content;
    private Boolean deleted;

}
