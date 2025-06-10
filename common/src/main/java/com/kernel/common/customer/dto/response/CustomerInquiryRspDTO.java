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
public class CustomerInquiryRspDTO {

    // 수요자 게시글ID
    private Long inquiryId;

    // 게시글 카테고리ID
    private Long categoryId;

    // 게시글 카테고리 이름
    private String categoryName;

    // 제목
    private String title;

    // 작성일시
    private LocalDateTime createdAt;

    // 답변 여부
    private Boolean isReplied;

}
