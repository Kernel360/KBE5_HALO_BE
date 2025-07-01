package com.kernel.global.service.dto.condition;

import com.kernel.global.common.enums.UserStatus;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Builder
@Schema(description = "관리자 사용자 검색 조건 DTO")
public class AdminUserSearchCondition {

    @Max(value = 15, message = "전화번호는 최대 15자까지 입력 가능합니다.")
    @Schema(description = "전화번호", example = "01012345678", maxLength = 15)
    private String phone;

    @Max(value = 10, message = "사용자 이름은 최대 10자까지 입력 가능합니다.")
    @Schema(description = "사용자 이름", example = "홍길동", maxLength = 10)
    private String userName;

    @Max(value = 50, message = "이메일은 최대 50자까지 입력 가능합니다.")
    @Schema(description = "이메일 주소", example = "example@domain.com", maxLength = 50)
    private String email;

    @Schema(description = "사용자 상태", example = "ACTIVE")
    private UserStatus status;
}