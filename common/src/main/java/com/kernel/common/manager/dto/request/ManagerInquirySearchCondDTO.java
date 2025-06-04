package com.kernel.common.manager.dto.request;

import com.kernel.common.global.enums.ReplyStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class ManagerInquirySearchCondDTO {

    // 작성일시 시작일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromCreatedAt;

    // 작성일시 종료일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toCreatedAt;

    // 답변상태
    private ReplyStatus replyStatus;

    // 제목키워드
    private String titleKeyword;

    // 내용키워드
    private String contentKeyword;

    public LocalDateTime getFromDateTime() {
        // fromCreatedAt - LocalDate → LocalDateTime 보정
        return fromCreatedAt != null ? fromCreatedAt.atStartOfDay() : null;
    }

    public LocalDateTime getToDateTime() {
        // toCreatedAt - LocalDate → LocalDateTime 보정
        return toCreatedAt != null ? toCreatedAt.atTime(23, 59, 59) : null;
    }
}
