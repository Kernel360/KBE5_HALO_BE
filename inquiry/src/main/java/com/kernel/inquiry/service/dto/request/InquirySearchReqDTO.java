package com.kernel.inquiry.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Schema(description = "문의 검색 요청 DTO")
public class InquirySearchReqDTO {

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

    // 작성자 역할
    @NotBlank(message = "작성자 역할을 입력해주세요.")
    private String authorRole;

    // LocalDate -> LocalDateTime 변환
    public LocalDateTime getFromCreatedAt() {
        return fromCreatedAt != null ? fromCreatedAt.atStartOfDay() : null;
    }

    public LocalDateTime getToCreatedAt() {
        return toCreatedAt != null ? toCreatedAt.atTime(23, 59, 59) : null;
    }

}
