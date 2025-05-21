package com.kernel360.Halo.web.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryResponseDTO {
    // id, authorId, title, content, createdAt, deleted

    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Boolean deleted;

    public boolean isDeleted() {
        return deleted != null && deleted;
    }
}
