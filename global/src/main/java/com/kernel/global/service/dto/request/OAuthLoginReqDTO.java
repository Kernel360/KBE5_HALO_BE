package com.kernel.global.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OAuthLoginReqDTO {
    @NotBlank(message = "인증 코드가 필요합니다.")
    private String code;
}
