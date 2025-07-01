package com.kernel.admin.service.dto.request;

import com.kernel.global.common.enums.UserStatus;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Schema(description = "관리자 회원가입 요청 DTO")
public class AdminSignupReqDTO {

    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    @Size(min = 10, max = 15, message = "핸드폰 번호는 10자 이상 15자 이하로 입력해주세요.")
    @Schema(description = "핸드폰 번호", example = "01012345678", minLength = 10, maxLength = 15)
    private String phone;

    @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
    @Size(max = 10, message = "이름은 10자 이하로 입력해주세요.")
    @Schema(description = "사용자 이름", example = "홍길동", maxLength = 10)
    private String userName;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Schema(description = "이메일 주소", example = "example@domain.com")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하로 입력해주세요.")
    @Schema(description = "비밀번호", example = "password123", minLength = 8, maxLength = 16)
    private String password;
}