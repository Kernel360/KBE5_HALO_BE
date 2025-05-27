package com.kernel.app.service;


import com.kernel.app.dto.request.CustomerSignupReqDTO;
import com.kernel.app.dto.request.UserLoginReqDTO;

public interface CustomerAuthService {

    public void join(CustomerSignupReqDTO joinDTO);

    public void login(UserLoginReqDTO loginDTO);
}
