package com.kernel.global.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.util.List;

@Getter
@Builder
@Schema(description = "프리사인 URL 응답 DTO")
public class PresignedUrlRspDTO {

    @Schema(description = "프리사인 URL 목록", example = "[\"https://example.com/file1\", \"https://example.com/file2\"]", required = true)
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