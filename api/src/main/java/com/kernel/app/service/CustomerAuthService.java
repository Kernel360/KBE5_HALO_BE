package com.kernel.app.service;

import com.kernel.common.customer.dto.request.CustomerSignupReqDTO;

public interface CustomerAuthService {

     void join(CustomerSignupReqDTO joinDTO);
}
