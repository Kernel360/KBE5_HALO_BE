package com.kernel.global.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.util.List;

@Getter
@Builder
@Schema(description = "Presigned URL 응답 DTO")
public class PresignedUrlRspDTO {

    @Schema(description = "Presigned URL 목록", example = "[\"https://example.com/file1\", \"https://example.com/file2\"]", required = true)
    private List<String> preSignedUrls;

    // PresignedPutObjectRequest to PresignedUrlRspDTO 변환 메소드
    public static PresignedUrlRspDTO fromPresignedUrls(List<PresignedPutObjectRequest> preSignedUrls) {
        return PresignedUrlRspDTO.builder()
                .preSignedUrls(preSignedUrls.stream()
                        .map(PresignedPutObjectRequest::url)
                        .map(Object::toString)
                        .toList())
                .build();

    }
}