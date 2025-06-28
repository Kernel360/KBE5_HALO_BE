package com.kernel.global.service.dto.condition;

import com.kernel.global.common.enums.UserStatus;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminUserSearchCondition {
    @Max(value = 15, message = "전화번호는 최대 15자까지 입력 가능합니다.")
    private String phone;

    @Max(value = 10, message = "사용자 이름은 최대 10자까지 입력 가능합니다.")
    private String userName;

    @Max(value = 50, message = "이메일은 최대 50자까지 입력 가능합니다.")
    private String email;

    private UserStatus status; // UserStatus enum의 이름을 문자열로 받음
}
