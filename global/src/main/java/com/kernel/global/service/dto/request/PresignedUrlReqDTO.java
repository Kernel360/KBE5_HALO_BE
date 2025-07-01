package com.kernel.global.service.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Getter
@Schema(description = "프리사인 URL 요청 DTO")
public class PresignedUrlReqDTO {

    @NotEmpty(message = "업로드할 파일 이름 목록은 필수입니다.")
    @Schema(description = "업로드할 파일 이름 목록", example = "[\"file1.txt\", \"file2.jpg\"]", required = true)
    private List<String> files;

    @Max(value = 60, message = "최대 60분까지 설정할 수 있습니다.")
    @Schema(description = "링크 만료 시간(분)", example = "30", minimum = "1", maximum = "60", defaultValue = "30")
    private Integer expirationMinutes = 30;

}