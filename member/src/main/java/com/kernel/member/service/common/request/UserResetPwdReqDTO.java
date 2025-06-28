package com.kernel.member.service.common.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResetPwdReqDTO {

    // 현재 비밀번호
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;

    // 새로운 비밀번호
    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    private String newPassword;

    // 새로운 비밀번호 확인
    @NotBlank(message = "새로운 비밀번호 확인을 입력해주세요.")
    private String confirmPassword;
}
