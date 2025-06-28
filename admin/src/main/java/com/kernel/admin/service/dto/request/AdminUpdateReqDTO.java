package com.kernel.admin.service.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AdminUpdateReqDTO {

    // 관리자 이름
    @Size(max = 10, message = "이름은 10자 이하로 입력해주세요.")
    private String userName;

    // 관리자 전화번호
    @Size(max = 15, message = "전화번호는 15자 이하로 입력해주세요.")
    private String phone;

    // 관리자 이메일
    @Size(max = 50, message = "이메일은 최대 50자까지 입력 가능합니다.")
    private String email;

    // 관리자 비밀번호
    private String password;

}
