package com.kernel.inquiry.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "문의 수정 요청 DTO")
public class InquiryUpdateReqDTO {

    @Schema(description = "문의 제목", example = "문의 제목 예시", required = true)
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 5, max = 50, message = "제목은 최소 5자, 최대 50자까지 입력할 수 있습니다.")
    private String title;

    @Schema(description = "문의 내용", example = "문의 내용 예시", required = true)
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 5, max = 5000, message = "내용은 최소 5자, 최대 5000자까지 입력할 수 있습니다.")
    private String content;

    @Schema(description = "첨부 파일 ID", example = "456", required = false)
    private Long fileId;

    @Schema(description = "문의 카테고리", example = "GENERAL", required = true)
    @NotNull(message = "카테고리를 선택해주세요.")
    private String category;


}