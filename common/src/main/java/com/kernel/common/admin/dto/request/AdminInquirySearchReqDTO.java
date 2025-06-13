package com.kernel.common.admin.dto.request;

import com.kernel.common.global.enums.ReplyStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fromCreatedAt;

    // 등록일 범위 종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime toCreatedAt;

    public ReplyStatus replyStatusFilter() {
        if (replyStatus == null || replyStatus.isEmpty()) {
            return null;
        }

        try {
            return ReplyStatus.valueOf(replyStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            // 유효하지 않은 상태 값인 경우 null 반환
            return null;
        }
    }
}
