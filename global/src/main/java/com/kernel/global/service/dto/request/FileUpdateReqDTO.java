package com.kernel.global.service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Getter
@Schema(description = "파일 업데이트 요청 DTO")
public class FileUpdateReqDTO {

    @NotNull(message = "파일 ID는 필수입니다.")
    @Schema(description = "파일 ID", example = "12345", required = true)
    private Long fileId;

    @NotEmpty(message = "파일 URL 목록은 필수입니다.")
    @Schema(description = "업데이트할 파일 URL 목록", example = "[\"https://example.com/file1\", \"https://example.com/file2\"]", required = true)
    private List<String> fileUrls;
}