package com.kernel.global.service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Getter
@Schema(description = "파일 업로드 요청 DTO")
public class FileUploadReqDTO {

    @NotEmpty(message = "파일 목록은 필수입니다.")
    @Schema(description = "업로드한 파일 URL 목록", example = "[\"https://example.com/file1\", \"https://example.com/file2\"]", required = true)
    private List<String> fileUrls;

}