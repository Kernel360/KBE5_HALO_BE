package com.kernel.member.service.request;

import com.kernel.member.service.common.request.UserInfoUpdateReqDTO;
import com.kernel.member.service.common.request.UserUpdateReqDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateReqDTO {

    // User
    @Valid
    private UserUpdateReqDTO userUpdateReqDTO;

    //UserInfo
    @Valid
    private UserInfoUpdateReqDTO userInfoUpdateReqDTO;
}
