package com.kernel.member.service.request;

import com.kernel.member.service.common.request.UserInfoSignupReqDTO;
import com.kernel.member.service.common.request.UserSignupReqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "고객 회원가입 요청 DTO")
public class CustomerSignupReqDTO {

    @Schema(description = "사용자 회원가입 정보", required = true)
    @Valid
    private UserSignupReqDTO userSignupReqDTO;

    @Schema(description = "사용자 추가 정보", required = true)
    @Valid
    private UserInfoSignupReqDTO userInfoSignupReqDTO;

}