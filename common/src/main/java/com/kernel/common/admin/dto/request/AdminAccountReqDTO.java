package com.kernel.common.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminAccountReqDTO {
    // TODO: 관리자 계정을 생성할 때 요청 DTO를 정의

    @NotBlank
    @Size(max = 50)
    private String id;

    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
}
