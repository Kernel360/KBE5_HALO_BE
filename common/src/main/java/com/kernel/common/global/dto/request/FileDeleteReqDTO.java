package com.kernel.common.global.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class FileDeleteReqDTO {

    // 게시물의 첨부파일 ID
    @NotNull(message = "파일 ID는 필수입니다.")
    private Long fileId;

    // 삭제할 파일 경로
    @NotEmpty(message = "삭제할 파일의 url는 필수입니다.")
    private List<String> s3Urls;
}
