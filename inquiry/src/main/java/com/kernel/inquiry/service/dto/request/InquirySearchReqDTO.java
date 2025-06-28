package com.kernel.inquiry.service.dto.request;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class InquirySearchReqDTO {

    // 작성일시 시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromCreatedAt;

    // 작성일시 종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toCreatedAt;

    // 답변상태
    private Boolean replyStatus;

    // 제목키워드
    private String titleKeyword;

    // 내용키워드
    private String contentKeyword;

    // LocalDate -> LocalDateTime 변환
    public LocalDateTime getFromCreatedAt() {
        return fromCreatedAt != null ? fromCreatedAt.atStartOfDay() : null;
    }

    public LocalDateTime getToCreatedAt() {
        return toCreatedAt != null ? toCreatedAt.atTime(23, 59, 59) : null;
    }

}
