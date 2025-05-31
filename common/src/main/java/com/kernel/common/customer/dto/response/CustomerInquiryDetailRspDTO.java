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
public class CustomerInquiryDetailRspDTO {

    // 수요자 게시글ID
    private Long inquiryId;

    // 작성자ID
    private Long authorId;

    // 카테고리ID
    private Long categoryId;

    // 카테고리명
    private String categoryName;

    // 제목
    private String title;

    // 내용
    private String content;

    //TODO 첨부파일 기능 추가
    // private Long fileId

    // 작성일시
    private LocalDateTime createdAt;

    // 답변
    private CustomerInquiryReplyRspDTO reply;

}
