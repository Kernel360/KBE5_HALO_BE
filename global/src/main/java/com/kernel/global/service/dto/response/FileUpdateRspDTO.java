package com.kernel.global.service.dto.response;

import com.kernel.global.domain.entity.File;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "파일 업데이트 응답 DTO")
public class FileUpdateRspDTO {

    @Schema(description = "업로드된 파일 객체 ID", example = "123", required = true)
    private Long fileId;

    @Schema(description = "업로드된 파일 경로의 JSON 형식 리스트", example = "[\"https://example.com/file1\", \"https://example.com/file2\"]", required = true)
    private String files;

    // File entity를 FileUpdateRspDTO로 변환하는 메소드
    public static FileUpdateRspDTO fromEntity(File file) {
        return FileUpdateRspDTO.builder()
                .fileId(file.getFileId())
                .files(file.getFilePathsJson())
                .build();
    }
}