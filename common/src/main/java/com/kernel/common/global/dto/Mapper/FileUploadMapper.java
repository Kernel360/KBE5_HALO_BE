package com.kernel.common.global.dto.Mapper;

import com.kernel.common.global.dto.response.FileDeleteRspDTO;
import com.kernel.common.global.dto.response.FileUploadRspDTO;
import com.kernel.common.global.entity.UploadedFiles;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileUploadMapper {

    // UploadedFiles Entity to FileUploadRspDTO
    public FileUploadRspDTO toFileUploadRspDTO(UploadedFiles uploadedFiles) {
        return FileUploadRspDTO.builder()
                .fileId(uploadedFiles.getId())
                .files(uploadedFiles.getFilePathsJson())
                .build();
    }

    // UploadedFiles Entity to FileDeleteRspDTO
    public FileDeleteRspDTO toFileDeleteRspDTO(UploadedFiles uploadedFiles) {
        return FileDeleteRspDTO.builder()
                .postId(uploadedFiles.getPostId())
                .remainingFiles(uploadedFiles.getFilePathsJson())
                .build();
    }
}
