package com.kernel.member.service.common.request;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import com.kernel.member.common.annotation.ValidUserSignup;
import com.kernel.global.common.enums.SocialProvider;
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

@ValidUserSignup
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
    private String password;

    // 계정 상태
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    // 소셜 로그인 제공자
    private SocialProvider provider;

    // 소셜 로그인 제공자의 고유 사용자 ID
    private String providerId;


    // UserSignupReqDTO -> User
    public User toEntityWithRole(UserRole role, BCryptPasswordEncoder encoder) {
        return User.builder()
                .phone(phone)
                .userName(userName)
                .email(email)
                .password(password != null ? encoder.encode(password) : null)
                .role(role)
                .status(status)
                .provider(provider != null ? provider : null)
                .providerId(providerId != null ? providerId : null)
                .build();
    }

}
