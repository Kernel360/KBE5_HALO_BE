package com.kernel.global.service.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class FileUpdateReqDTO {

    // 파일 ID
    @NotNull(message = "파일 ID는 필수입니다.")
    private Long fileId;

    // 업데이트할 파일 URL 목록
    @NotEmpty(message = "파일 URL 목록은 필수입니다.")
    private List<String> fileUrls;
}
