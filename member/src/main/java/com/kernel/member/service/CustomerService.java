package com.kernel.member.service;

import com.kernel.member.service.common.request.UserResetPwdReqDTO;
import com.kernel.member.service.request.CustomerSignupReqDTO;
import com.kernel.member.service.request.CustomerUpdateReqDTO;
import com.kernel.member.service.response.CustomerDetailRspDTO;

public interface CustomerService {

     // 수요자 회원가입
     void signup(CustomerSignupReqDTO signupReqDTO);

     // 수요자 정보 조회
     CustomerDetailRspDTO getCustomer(Long userId);

     // 수요자 정보 수정
     CustomerDetailRspDTO updateCustomer(Long userId, CustomerUpdateReqDTO updateReqDTO);
}
