package com.kernel.app.service;


import com.kernel.common.admin.dto.request.AdminSignupReqDTO;
import com.kernel.common.admin.dto.request.AdminUpdateReqDTO;

public interface AdminAuthService {

     void join(AdminSignupReqDTO joinDTO);
     void updateAdmin(Long adminID, AdminUpdateReqDTO updateDTO);
     void deleteAdmin(Long adminID);
}
