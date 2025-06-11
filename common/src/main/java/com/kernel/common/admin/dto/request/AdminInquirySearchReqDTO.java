package com.kernel.common.admin.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminInquirySearchReqDTO {

    // 작성자 이름
    private String authorName;

    // 제목
    private String title;

    // 카테고리
    private String category;

    // 내용
    private String content;

    // 답변 상태
    private String replyStatus;

    // 등록일 범위 시작일
    private LocalDateTime fromCreatedAt;

    // 등록일 범위 종료일
    private LocalDateTime toCreatedAt;
}
