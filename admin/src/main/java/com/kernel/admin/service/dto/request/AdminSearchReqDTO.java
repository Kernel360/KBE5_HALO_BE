package com.kernel.admin.service.dto.request;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.service.dto.condition.AdminUserSearchCondition;

import jakarta.validation.constraints.Max;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "관리자 검색 요청 DTO")
public class AdminSearchReqDTO {

    @Max(value = 15, message = "전화번호는 최대 15자까지 입력 가능합니다.")
    @Schema(description = "전화번호", example = "01012345678", maximum = "15")
    private String phone;

    @Max(value = 10, message = "사용자 이름은 최대 10자까지 입력 가능합니다.")
    @Schema(description = "사용자 이름", example = "홍길동", maximum = "10")
    private String userName;

    @Max(value = 50, message = "이메일은 최대 50자까지 입력 가능합니다.")
    @Schema(description = "이메일 주소", example = "example@domain.com", maximum = "50")
    private String email;

    @Schema(description = "사용자 상태", example = "ACTIVE")
    private List<UserStatus> status; // UserStatus enum의 이름을 문자열로 받음

    // AdminUserSearchCondition에서 사용되는 필드들 매핑
    public AdminUserSearchCondition toCondition() {
        return AdminUserSearchCondition.builder()
                .phone(this.phone)
                .userName(this.userName)
                .email(this.email)
                .status(this.status)
                .build();
    }
}