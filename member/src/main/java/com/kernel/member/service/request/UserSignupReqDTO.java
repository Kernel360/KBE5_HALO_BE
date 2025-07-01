package com.kernel.member.service.request;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Schema(description = "사용자 회원가입 요청 DTO")
public class UserSignupReqDTO {

    @Schema(description = "핸드폰 번호", example = "010-1234-5678", required = true)
    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;

    @Schema(description = "이름", example = "홍길동", required = true)
    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @Schema(description = "이메일 주소", example = "example@email.com", required = true, maxLength = 100)
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @Size(max = 100, message = "이메일은 100자 이내로 입력해주세요.")
    private String email;

    @Schema(description = "비밀번호", example = "password123", required = true)
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Schema(description = "사용자 권한", example = "USER", required = true)
    private UserRole userRole;

    @Schema(description = "계정 상태", example = "ACTIVE", required = true)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @Schema(description = "UserSignupReqDTO를 User 엔티티로 변환")
    public User toEntityWithRole(UserSignupReqDTO reqDTO, UserRole role) {
        return User.builder()
                .phone(reqDTO.getPhone())
                .userName(reqDTO.getUserName())
                .email(reqDTO.getEmail())
                .password(reqDTO.getPassword())
                .role(role)
                .status(reqDTO.getStatus())
                .build();
    }
}