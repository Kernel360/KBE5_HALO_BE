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
public class CustomerInquiryReplyRspDTO {

    // 답글 내용
    private String content;

    // TODO 첨부파일 추가
    // private Long fileId

    // 생성날짜
    private LocalDateTime createdAt;

}
