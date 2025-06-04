package com.kernel.app.service;

import com.kernel.common.customer.dto.request.CustomerFindAccountReqDTO;
import com.kernel.common.customer.dto.request.CustomerInfoUpdateReqDTO;
import com.kernel.common.customer.dto.request.CustomerPasswordResetReqDTO;
import com.kernel.common.customer.dto.request.CustomerSignupReqDTO;
import com.kernel.common.customer.dto.response.CustomerInfoDetailRspDTO;
import jakarta.validation.Valid;

public interface CustomerAuthService {

     // 수요자 회원가입
     void signup(CustomerSignupReqDTO signupReqDTO);

     // 수요자 정보 조회
     CustomerInfoDetailRspDTO getCustomer(Long customerId);

     // 수요자 정보 수정
     CustomerInfoDetailRspDTO updateCustomer(Long customerId, @Valid CustomerInfoUpdateReqDTO updateReqDTO);

     // 수요자 비밀번호 변경
     void resetPassword(Long customerId, @Valid CustomerPasswordResetReqDTO resetReqDTO);

     // 수요자 회원 탈퇴
     void deleteCustomer(Long customerId, String password);

     // 수요자 아이디 찾기
     Boolean findCustomerId(CustomerFindAccountReqDTO findIdReqDTO);

     // 수요자 비밀번호 찾기
     String findCustomerPassword(CustomerFindAccountReqDTO findAccountReqDTO);
}
