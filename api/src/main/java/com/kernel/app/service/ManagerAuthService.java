package com.kernel.app.service;

import com.kernel.common.manager.dto.ManagerSignupReqDTO;

public interface ManagerAuthService {

     void join(ManagerSignupReqDTO joinDTO);
}
