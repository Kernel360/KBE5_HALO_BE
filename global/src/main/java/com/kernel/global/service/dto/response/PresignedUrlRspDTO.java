package com.kernel.global.service.dto.response;

import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.util.List;

@Getter
@Builder
public class PresignedUrlRspDTO {

    // Presigned URL 목록
    private List<String> preSignedUrls;

    // PresignedPutObjectRequest to PresignedUrlRspDTO 변환 메소드
    public static List<PresignedUrlRspDTO> fromPresignedUrls(List<PresignedPutObjectRequest> preSignedUrls) {
        return preSignedUrls.stream()
                .map(url -> PresignedUrlRspDTO.builder()
                        .preSignedUrls(List.of(url.url().toString()))
                        .build())
                .toList();
    }
}
