package com.kernel.app.service;


import com.kernel.app.dto.request.CustomerSignupReqDTO;

public interface CustomerAuthService {

     void join(CustomerSignupReqDTO joinDTO);
}
