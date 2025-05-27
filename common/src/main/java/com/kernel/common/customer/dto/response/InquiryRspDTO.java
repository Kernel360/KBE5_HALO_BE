package com.kernel.common.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryRspDTO {
    // id, authorId, title, content, createdAt

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

}
