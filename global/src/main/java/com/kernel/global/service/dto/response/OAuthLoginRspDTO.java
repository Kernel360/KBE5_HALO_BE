package com.kernel.global.service.dto.response;

import com.kernel.global.common.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthLoginRspDTO {

    private final boolean isNew;

    private final String email;
    private final String userName;
    private final String role;

    // 기존 사용자일 경우에만 포함
    private final String phone;
    private final Long userId;
    private final UserStatus status;

    // 신규 사용자일 경우에만 포함
    private final String password;

    // 기존 사용자 응답
    public static OAuthLoginRspDTO ofExistingUser(String name, String role, String phone, Long userId, UserStatus status) {
        return OAuthLoginRspDTO.builder()
                .isNew(false)
                .userName(name)
                .role(role)
                .phone(phone)
                .userId(userId)
                .status(status)
                .build();
    }

    // 신규 사용자 응답
    public static OAuthLoginRspDTO ofNewUser(String email, String name, String role, String password) {
        return OAuthLoginRspDTO.builder()
                .isNew(true)
                .email(email)
                .userName(name)
                .role(role)
                .password(password)
                .build();
    }
}