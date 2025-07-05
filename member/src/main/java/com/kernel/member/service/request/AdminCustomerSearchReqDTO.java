package com.kernel.member.service.request;

import com.kernel.global.common.enums.UserStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Schema(description = "관리자 고객 조회 요청 DTO")
public class AdminCustomerSearchReqDTO {

    @Schema(description = "고객 이름")
    private String userName;

    @Schema(description = "고객 연락처")
    private String phone;

    @Schema(description = "고객 이메일")
    private String email;

    @Schema(description = "고객 상태")
    private List<UserStatus> status;

}