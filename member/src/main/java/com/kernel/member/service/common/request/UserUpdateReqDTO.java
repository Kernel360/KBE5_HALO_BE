package com.kernel.member.service.common.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateReqDTO {

    // 이메일
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @Size(max = 100, message = "이메일은 100자 이내로 입력해주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

}
