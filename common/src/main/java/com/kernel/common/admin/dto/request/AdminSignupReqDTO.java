package com.kernel.common.admin.dto.request;

import com.kernel.common.global.enums.UserStatus;

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
    private String phone;

    @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
    private String userName;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;


}
