package com.kernel.global.domain.entity;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
    // 사용자 ID
    private Long userId;

    // 사용자 연락처
    private String phone;

    // 사용자 이름
    private String userName;

    // 사용자 이메일
    private String email;

    // 사용자 비밀번호
    private String password;

    // 사용자 권한
    private UserRole role;

    // 사용자 상태값
    private UserStatus status;
}
