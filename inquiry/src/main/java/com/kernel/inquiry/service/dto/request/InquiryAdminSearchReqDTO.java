package com.kernel.inquiry.service.dto.request;

import com.kernel.inquiry.common.enums.AuthorType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(description = "관리자 문의 검색 요청 DTO")
@ToString
public class InquiryAdminSearchReqDTO {

    @Schema(description = "문의 작성자 ID", example = "12345", required = false)
    private Long authorId;

    @Schema(description = "작성일시 시작일", example = "2023-01-01", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromCreatedAt;

    @Schema(description = "작성일시 종료일", example = "2023-12-31", required = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toCreatedAt;

    @Schema(description = "답변 상태", example = "true", required = false)
    private Boolean replyStatus;

    @Schema(description = "제목 키워드", example = "문의 제목 예시", required = false)
    private String titleKeyword;

    @Schema(description = "내용 키워드", example = "문의 내용 예시", required = false)
    private String contentKeyword;

    @Schema(description = "작성자 타입", example = "CUSTOMER", required = false)
    private AuthorType authorType;

    @Schema(description = "작성자 이름", example = "홍길동", required = false)
    private String userName;

    @Schema(description = "카테고리", example = "RESERVATION", required = false)
    private List<String> categories;

    // LocalDate -> LocalDateTime 변환
    public LocalDateTime getFromCreatedAt() {
        return fromCreatedAt != null ? fromCreatedAt.atStartOfDay() : null;
    }

    public LocalDateTime getToCreatedAt() {
        return toCreatedAt != null ? toCreatedAt.atTime(23, 59, 59) : null;
    }
}
