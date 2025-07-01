package com.kernel.common.admin.dto.request;

import com.kernel.common.global.enums.UserStatus;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSearchReqDTO {

    @Max(value = 15, message = "전화번호는 최대 15자까지 입력 가능합니다.")
    private String phone;

    @Max(value = 10, message = "사용자 이름은 최대 10자까지 입력 가능합니다.")
    private String userName;

    @Max(value = 50, message = "이메일은 최대 50자까지 입력 가능합니다.")
    private String email;

    private UserStatus status; // UserStatus enum의 이름을 문자열로 받음

}
