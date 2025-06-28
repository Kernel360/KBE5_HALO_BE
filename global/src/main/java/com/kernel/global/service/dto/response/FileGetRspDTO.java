package com.kernel.global.service.dto.response;

import com.kernel.global.domain.entity.File;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileGetRspDTO {
    // 파일 URL
    private String fileUrls;

    // File Entity to FileGetRspDTO 변환 메소드
    public static FileGetRspDTO fromEntity(File file) {
        return FileGetRspDTO.builder()
                .fileUrls(file.getFilePathsJson())
                .build();
    }
}
