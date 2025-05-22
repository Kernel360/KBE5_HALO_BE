package com.kernel.customer.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryRequestDTO {
    // categoryId, title, content

    private Long authorId;
    private String title;
    private String content;


}
