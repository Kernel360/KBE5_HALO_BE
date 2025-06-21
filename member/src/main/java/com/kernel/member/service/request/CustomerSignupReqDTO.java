package com.kernel.member.service.request;

import com.kernel.member.service.common.request.UserInfoSignupReqDTO;
import com.kernel.member.service.common.request.UserSignupReqDTO;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerSignupReqDTO {

    // User
    @Valid
    private UserSignupReqDTO userSignupReqDTO;

    // UserInfo
    @Valid
    private UserInfoSignupReqDTO userInfoSignupReqDTO;

    // Customer
    @Valid
    private CustomerReqDTO customerReqDTO;


}


