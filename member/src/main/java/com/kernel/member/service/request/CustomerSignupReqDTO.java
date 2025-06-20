package com.kernel.member.service.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerSignupReqDTO {

    // User
    private UserSignupReqDTO userSignupReqDTO;

    // UserInfo
    private UserInfoSignupReqDTO userInfoSignupReqDTO;

    // Customer
    private CustomerReqDTO customerReqDTO;


}


