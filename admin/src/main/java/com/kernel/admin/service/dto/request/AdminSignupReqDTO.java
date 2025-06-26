package com.kernel.admin.service.dto.request;

import com.kernel.global.common.enums.UserStatus;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AdminSignupReqDTO {

    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    @Size(min = 10, max = 15, message = "핸드폰 번호는 10자 이상 15자 이하로 입력해주세요.")
    private String phone;

    @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
    @Size(max = 10, message = "이름은 10자 이하로 입력해주세요.")
    private String userName;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요.")
    private String password;
}
