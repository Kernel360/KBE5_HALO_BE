package com.kernel.app.service;

import com.kernel.common.manager.dto.request.ManagerSignupReqDTO;
import com.kernel.common.manager.dto.request.ManagerTerminationReqDTO;
import com.kernel.common.manager.dto.request.ManagerUpdateReqDTO;
import com.kernel.common.manager.dto.response.ManagerInfoRspDTO;

public interface ManagerAuthService {

     /**
      * 매니저 회원가입
      * @param signupReqDTO 회원가입 요청 DTO
      */
     void signup(ManagerSignupReqDTO signupReqDTO);

     /**
      * 매니저 정보 조회
      * @param managerId 매니저ID
      * @return 매니저 정보를 담은 응답 DTO
      */
     ManagerInfoRspDTO getManager(Long managerId);

     /**
      * 매니저 정보 수정
      * @param managerId 매니저ID
      * @param updateReqDTO 매니저 정보 수정 요청 DTO
      */
     void updateManager(Long managerId, ManagerUpdateReqDTO updateReqDTO);

     /**
      * 매니저 계약 해지 요청
      * @param managerId 매니저ID
      * @param terminationReqDTO 매니저 계약 해지 요청 DTO
      */
     void requestManagerTermination(Long managerId, ManagerTerminationReqDTO terminationReqDTO);
}
