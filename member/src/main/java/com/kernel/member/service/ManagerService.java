package com.kernel.member.service;

import com.kernel.member.service.request.ManagerSignupReqDTO;
import com.kernel.member.service.request.ManagerUpdateReqDTO;
import com.kernel.member.service.response.ManagerDetailRspDTO;

public interface ManagerService {

    // 매니저 회원가입
    void signup(ManagerSignupReqDTO request);

    // 매니저 회원 정보 조회
    ManagerDetailRspDTO getManager(Long userId);

    // 매니저 회원 정보 수정
    ManagerDetailRspDTO updateManager(Long userId, ManagerUpdateReqDTO updateReqDTO);

}
