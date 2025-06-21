package com.kernel.global.service.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class PresignedUrlReqDTO {

    // 업로드할 파일의 이름 목록
    @NotEmpty
    private List<String> files;

    // 링크 만료 시간
    @Max(value = 60, message = "최대 60분까지 설정할 수 있습니다.")
    private Integer expirationMinutes = 30;

}
