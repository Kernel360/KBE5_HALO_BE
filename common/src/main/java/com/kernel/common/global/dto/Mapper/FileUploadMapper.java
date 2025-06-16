package com.kernel.common.global.dto.Mapper;

import com.kernel.common.global.dto.response.FileDeleteRspDTO;
import com.kernel.common.global.dto.response.FileUploadRspDTO;
import com.kernel.common.global.dto.response.PresignedUrlRspDTO;
import com.kernel.common.global.entity.UploadedFiles;

import com.kernel.common.global.enums.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FileUploadMapper {

    // PresignedPutObjectRequest to PresignedUrlRspDTO
    public List<PresignedUrlRspDTO> toPresignedUrlRspDTO(List<PresignedPutObjectRequest> preSignedUrls) {
        return preSignedUrls.stream()
                .map(url -> PresignedUrlRspDTO.builder()
                        .preSignedUrls(List.of(url.url().toString()))
                        .build())
                .toList();
    }

    // FilePathJson to UploadedFiles
    public UploadedFiles toUploadedFiles(String filePathJson) {
        return UploadedFiles.builder()
                .filePathsJson(filePathJson)
                .postStatus(PostStatus.REGISTERED)
                .build();
    }

    // UploadedFiles Entity to FileUploadRspDTO
    public FileUploadRspDTO toFileUploadRspDTO(UploadedFiles uploadedFiles) {
        return FileUploadRspDTO.builder()
                .fileId(uploadedFiles.getFileId())
                .files(uploadedFiles.getFilePathsJson())
                .build();
    }
}
