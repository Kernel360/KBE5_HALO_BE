package com.kernel.member.service.customer;

import com.kernel.member.service.request.CustomerSignupReqDTO;

public interface CustomerAuthService {

     // 수요자 회원가입
     void signup(CustomerSignupReqDTO signupReqDTO);

     // 수요자 정보 조회
     CustomerInfoDetailRspDTO getCustomer(Long customerId);

     // 수요자 정보 수정
     CustomerInfoDetailRspDTO updateCustomer(Long customerId, CustomerInfoUpdateReqDTO updateReqDTO);

     // 수요자 비밀번호 변경
     void resetPassword(Long customerId, CustomerPasswordResetReqDTO resetReqDTO);

     // 수요자 회원 탈퇴
     void deleteCustomer(Long customerId, String password);

     // 수요자 아이디 찾기
     Boolean findCustomerId(CustomerFindAccountReqDTO findIdReqDTO);

     // 수요자 비밀번호 찾기
     String findCustomerPassword(CustomerFindAccountReqDTO findAccountReqDTO);
}
