package com.kernel360.Halo.web.customer.dto.request;

import com.kernel360.Halo.domain.customer.entity.Inquiry;
import lombok.*;

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
