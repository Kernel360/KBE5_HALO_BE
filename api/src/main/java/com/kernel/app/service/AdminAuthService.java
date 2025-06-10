package com.kernel.app.service;


import com.kernel.common.admin.dto.request.AdminSignupReqDTO;

public interface AdminAuthService {

     void join(AdminSignupReqDTO joinDTO);
}
