package com.kernel.inquiry.service.dto.request;

import com.kernel.inquiry.domain.entity.Inquiry;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "문의 수정 요청 DTO")
public class InquiryUpdateReqDTO {

    @Schema(description = "수정할 문의사항 ID", example = "123", required = true)
    @NotNull(message = "수정할 문의사항을 선택해주세요.")
    private Long inquiryId;

    @Schema(description = "문의 제목", example = "문의 제목 예시", required = false)
    @Size(max = 50, message = "제목은 최대 50자까지 입력할 수 있습니다.")
    private String title;

    @Schema(description = "문의 내용", example = "문의 내용 예시", required = false)
    @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다.")
    private String content;

    @Schema(description = "첨부 파일 ID", example = "456", required = false)
    private Long fileId;

    @Schema(description = "문의 카테고리", example = "GENERAL", required = false)
    private Enum<?> category;

    public static Inquiry toEntity(InquiryUpdateReqDTO request) {
        return Inquiry.builder()
                .inquiryId(request.getInquiryId())
                .title(request.getTitle() != null ? request.getTitle() : null)
                .content(request.getContent() != null ? request.getContent() : null)
                .fileId(request.getFileId() != null ? request.getFileId() : null)
                .category(request.getCategory() != null ? request.getCategory() : null)
                .build();
    }
}