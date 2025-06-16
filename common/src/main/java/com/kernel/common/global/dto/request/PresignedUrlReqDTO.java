package com.kernel.common.global.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class PresignedUrlReqDTO {

    @NotEmpty
    private List<String> files;

    @Max(value = 60, message = "최대 60분까지 설정할 수 있습니다.")
    private Integer expirationMinutes = 30;

}
