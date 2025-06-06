package com.kernel.common.global.util;

import com.kernel.common.config.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class S3Processor {

    private final S3Client s3Client;
    private final AwsProperties awsProperties;

    /**
     * S3에 파일을 업로드합니다.
     *
     * @param file     업로드할 파일
     * @param dirName  S3 버킷 내 디렉토리 이름
     * @return 업로드된 파일의 URL
     */
    public String upload(MultipartFile file, String dirName) {
        String bucket = awsProperties.getS3().getBucket();

       String originalFileName = file.getOriginalFilename();
       String fileName = dirName + "/" + originalFileName;

       try {
           PutObjectRequest putRequest = PutObjectRequest.builder()
                   .bucket(bucket)
                   .key(fileName)
                   .acl(ObjectCannedACL.PUBLIC_READ)    // 파일 공개 설정
                   .contentType(file.getContentType())
                   .build();

           s3Client.putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

           return "https://" + bucket + ".s3.amazonaws.com/" + fileName; // 업로드된 파일의 URL 반환
       } catch (IOException e) {
           throw new RuntimeException("S3 업로드 실패: ", e);
       }
    }

    /**
     * S3에서 파일을 삭제합니다.
     *
     * @param fileUrl 삭제할 파일의 URL
     */
    public void delete(String fileUrl) {
        String bucket = awsProperties.getS3().getBucket();

        String Key = extractKeyFromUrl(fileUrl);

        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(Key)
                .build();

        s3Client.deleteObject(deleteRequest);
    }

    private String extractKeyFromUrl(String fileUrl) {
        return fileUrl.substring(fileUrl.indexOf(".com/") + 5); // "https://{bucket}.s3.amazonaws.com/" 이후의 키 추출
    }

}
