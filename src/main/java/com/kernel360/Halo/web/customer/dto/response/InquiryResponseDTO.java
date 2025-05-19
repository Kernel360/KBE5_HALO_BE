package com.kernel360.Halo.web.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
public class InquiryResponseDTO {
    // id, authorId, title, content, createdAt

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

}
