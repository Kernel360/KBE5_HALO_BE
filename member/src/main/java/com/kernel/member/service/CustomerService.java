package com.kernel.member.service;

import com.kernel.member.service.request.CustomerSignupReqDTO;
import com.kernel.member.service.request.CustomerUpdateReqDTO;
import com.kernel.member.service.response.CustomerDetailRspDTO;
import jakarta.validation.constraints.NotNull;

public interface CustomerService {

     // 수요자 회원가입
     void signup(CustomerSignupReqDTO signupReqDTO);

     // 수요자 정보 조회
     CustomerDetailRspDTO getCustomer(Long userId);

     // 수요자 정보 수정
     CustomerDetailRspDTO updateCustomer(Long userId, CustomerUpdateReqDTO updateReqDTO);

     // 포인트 조회
     Integer getCustomerPoints(Long userId);

     // 포인트 차감
    void payByPoint(Long userId, Integer amount);
}
