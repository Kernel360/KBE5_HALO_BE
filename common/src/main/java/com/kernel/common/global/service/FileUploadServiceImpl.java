package com.kernel.common.global.service;

import com.kernel.common.config.AwsProperties;
import com.kernel.common.config.S3Config;
import com.kernel.common.global.dto.request.PresignedUrlReqDTO;
import com.kernel.common.global.dto.response.PresignedUrlRspDTO;
import com.kernel.common.global.util.ListJsonConverter;
import com.kernel.common.global.util.S3Processor;
import com.kernel.common.global.dto.Mapper.FileUploadMapper;
import com.kernel.common.global.dto.request.FileDeleteReqDTO;
import com.kernel.common.global.dto.request.FileUploadReqDTO;
import com.kernel.common.global.dto.response.FileDeleteRspDTO;
import com.kernel.common.global.dto.response.FileUploadRspDTO;
import com.kernel.common.global.entity.UploadedFiles;
import com.kernel.common.global.repository.UploadedFileRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.net.URL;
import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService{

    private final AwsProperties awsProperties;
    private final S3Processor s3Processor;
    private final S3Presigner s3Presigner;
    private final UploadedFileRepository uploadedFileRepository;
    private final FileUploadMapper fileUploadMapper;
    private final ListJsonConverter listJsonConverter;

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

        return fileUploadMapper.toPresignedUrlRspDTO(presignedUrls);
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
        UploadedFiles existingFiles;

        // UploadedFiles 엔티티가 존재하지 않으면 새로 생성
        if (request.getFileId() == null) {
            existingFiles = UploadedFiles.builder().filePathsJson("[]").build();
        } else {
            existingFiles = uploadedFileRepository.findById(request.getFileId())
                    .orElseThrow(() -> new NoSuchElementException("File을 찾을 수 없습니다."));
        }

        List<String> fileUrls = listJsonConverter.parseJsonToList((existingFiles.getFilePathsJson()));

        for (MultipartFile file : request.getFiles()) {
            // S3에 파일 업로드
            String s3Url = s3Processor.upload(file, "uploads");

            // fileUrls에 업로드된 파일 URL 추가
            if (!fileUrls.contains(s3Url)) {
                fileUrls.add(s3Url);
            }
        }

        existingFiles.updateFiles(listJsonConverter.convertListToJson(fileUrls));

        return fileUploadMapper.toFileUploadRspDTO(uploadedFileRepository.save(existingFiles)); // uploadedFiles에 save를 사용한 이유는 새로운 엔터티를 생성한 경우가 있기 때문
    }
}
