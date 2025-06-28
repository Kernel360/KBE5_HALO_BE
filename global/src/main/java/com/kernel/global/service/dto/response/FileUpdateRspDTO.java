package com.kernel.global.service.dto.response;

import com.kernel.global.domain.entity.File;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileUpdateRspDTO {

    // UploadedFiles 객체 ID
    private Long fileId;

    // 업로드된 파일 경로의 Json 형식 리스트
    private String files;

    // File entity를 FileUploadRspDTO로 변환하는 메소드
    public static FileUpdateRspDTO fromEntity(File file) {
        return FileUpdateRspDTO.builder()
                .fileId(file.getFileId())
                .files(file.getFilePathsJson())
                .build();
    }
}
