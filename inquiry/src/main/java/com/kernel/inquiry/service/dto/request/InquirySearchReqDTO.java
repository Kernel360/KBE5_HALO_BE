package com.kernel.inquiry.service.dto.request;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class InquirySearchReqDTO {

    // 작성일시 시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fromCreatedAt;

    // 작성일시 종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime toCreatedAt;

    // 답변상태
    private Boolean replyStatus;

    // 제목키워드
    private String titleKeyword;

    // 내용키워드
    private String contentKeyword;

}
