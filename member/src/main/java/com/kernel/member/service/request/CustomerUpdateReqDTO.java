package com.kernel.member.service.request;

import com.kernel.member.service.common.request.UserInfoUpdateReqDTO;
import com.kernel.member.service.common.request.UserUpdateReqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "고객 수정 요청 DTO")
public class CustomerUpdateReqDTO {

    @Schema(description = "사용자 수정 정보", required = true)
    @Valid
    private UserUpdateReqDTO userUpdateReqDTO;

    @Schema(description = "사용자 추가 수정 정보", required = true)
    @Valid
    private UserInfoUpdateReqDTO userInfoUpdateReqDTO;
}