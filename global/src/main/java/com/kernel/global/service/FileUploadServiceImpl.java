package com.kernel.global.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kernel.global.common.enums.PostStatus;
import com.kernel.global.common.properties.AwsProperties;
import com.kernel.global.domain.entity.File;
import com.kernel.global.repository.FileRepository;
import com.kernel.global.service.dto.request.FileUpdateReqDTO;
import com.kernel.global.service.dto.request.FileUploadReqDTO;
import com.kernel.global.service.dto.request.PresignedUrlReqDTO;
import com.kernel.global.service.dto.response.FileGetRspDTO;
import com.kernel.global.service.dto.response.FileUpdateRspDTO;
import com.kernel.global.service.dto.response.FileUploadRspDTO;
import com.kernel.global.service.dto.response.PresignedUrlRspDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final AwsProperties awsProperties;
    private final S3Presigner s3Presigner;
    private final FileRepository fileRepository;

    /**
     * 파일 목록 조회 메서드
     * 이 메서드는 파일 ID를 기반으로 S3에 저장된 파일 목록을 조회합니다.
     * @param fileId 조회할 파일 ID
     * @return 파일 목록 응답 DTO
     */
    @Transactional(readOnly = true)
    @Override
    public FileGetRspDTO getFileList(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));

        return FileGetRspDTO.fromEntity(file);
    }

    /**
     * Presigned URL 생성 메서드
     * 이 메서드는 S3에 파일을 업로드하기 위한 Presigned URL을 생성합니다.
     * @param request Presigned URL 요청 DTO
     * @return Presigned URL 응답 DTO
     */
    @Override
    public List<PresignedUrlRspDTO> generatePresignedUrls(PresignedUrlReqDTO request) {
        List<PresignedPutObjectRequest> presignedUrls = new ArrayList<>();

        for (String file : request.getFiles()) {
            String uuid = UUID.randomUUID().toString();
            String fileName = file;
            String s3Key = String.format("uploads/%s/%s", uuid, fileName);  // S3에 저장할 파일 경로

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(awsProperties.getS3().getBucket())
                    .key(s3Key)
                    .build();

            PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(r -> r
                    .putObjectRequest(objectRequest)
                    .signatureDuration(Duration.ofMinutes(request.getExpirationMinutes())));
            presignedUrls.add(presignedRequest);
        }

        return PresignedUrlRspDTO.fromPresignedUrls(presignedUrls);
    }

    /**
     * 첨부파일 업로드 메서드
     * 이 메서드는 파일 업로드 요청을 처리하고, S3에 파일을 업로드한 후, UploadedFiles 엔티티에 파일 경로를 저장합니다.
     * @param request 파일 업로드 요청 DTO
     * @return 업로드된 파일 정보가 담긴 응답 DTO
     */
    @Transactional
    @Override
    public FileUploadRspDTO uploadFiles(FileUploadReqDTO request) {
        Gson gson = new Gson();
        String filePathsJson;

        try {
            filePathsJson = gson.toJson(request.getFileUrls());
        } catch (JsonSyntaxException e) {
            throw new NoSuchElementException("파일 경로를 JSON으로 변환하는 중 오류가 발생했습니다.");
        }

        File uploadedFiles = File.builder()
                .filePathsJson(filePathsJson)
                .postStatus(PostStatus.REGISTERED)
                .build();

        fileRepository.save(uploadedFiles); // 파일 엔티티를 저장

        return FileUploadRspDTO.fromEntity(uploadedFiles);
    }

    /**
     * 파일 업데이트 메서드
     * 이 메서드는 파일 업로드 요청을 처리하고, S3에 파일을 업로드한 후, UploadedFiles 엔티티에 파일 경로를 업데이트합니다.
     * @param request 파일 업로드 요청 DTO
     */
    @Transactional
    @Override
    public FileUpdateRspDTO updateFiles(FileUpdateReqDTO request) {
        Gson gson = new Gson();
        String filePathsJson;

        try {
            filePathsJson = gson.toJson(request.getFileUrls());
        } catch (JsonSyntaxException e) {
            throw new NoSuchElementException("파일 경로를 JSON으로 변환하는 중 오류가 발생했습니다: " + e.getMessage());
        }

        File existingFiles = fileRepository.findById(request.getFileId())
                .orElseThrow(() -> new IllegalArgumentException("업데이트할 파일을 찾을 수 없습니다."));

        existingFiles.updateFiles(filePathsJson);

        return FileUpdateRspDTO.fromEntity(existingFiles);
    }
}
