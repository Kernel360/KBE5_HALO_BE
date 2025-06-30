package com.kernel.global.service.dto.response;

import com.kernel.global.domain.entity.File;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "파일 조회 응답 DTO")
public class FileGetRspDTO {

    @Schema(description = "파일 URL", example = "https://example.com/file1", required = true)
    private String fileUrls;

    // File Entity to FileGetRspDTO 변환 메소드
    public static FileGetRspDTO fromEntity(File file) {
        return FileGetRspDTO.builder()
                .fileUrls(file.getFilePathsJson())
                .build();
    }
}