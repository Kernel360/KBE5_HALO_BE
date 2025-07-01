package com.kernel.common.admin.dto.request;

import jakarta.validation.constraints.Max;
import lombok.Getter;

@Getter
public class AdminUpdateReqDTO {

    // 관리자 이름
    @Max(value = 10, message = "사용자 이름은 최대 10자까지 입력 가능합니다.")
    private String userName;

    // 관리자 전화번호
    @Max(value = 15, message = "전화번호는 최대 15자까지 입력 가능합니다.")
    private String phone;

    // 관리자 이메일
    @Max(value = 50, message = "이메일은 최대 50자까지 입력 가능합니다.")
    private String email;

    // 관리자 상태
    private String status; // UserStatus enum의 이름을 문자열로 받음

    // 관리자 비밀번호
    private String password;

}
