package com.kernel.member.service.common.request;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupReqDTO {

    // 핸드폰 번호
    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;

    // 이름
    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    // 이메일
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @Size(max = 100, message = "이메일은 100자 이내로 입력해주세요.")
    private String email;

    // 비밀번호
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    // 계정 상태
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;


    // UserSignupReqDTO -> User
    public User toEntityWithRole(UserRole role, BCryptPasswordEncoder encoder) {
        return User.builder()
                .phone(phone)
                .userName(userName)
                .email(email)
                .password(encoder.encode(password))
                .role(role)
                .status(status)
                .build();
    }

}
