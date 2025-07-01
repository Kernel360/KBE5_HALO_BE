package com.kernel.member.service.common;

import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import com.kernel.member.service.common.request.UserFindAccountReqDTO;
import com.kernel.member.service.common.request.UserResetPwdReqDTO;
import com.kernel.member.service.common.request.UserSignupReqDTO;
import jakarta.validation.Valid;

public interface UserService {

    // 중복 사용자 확인
    void validateDuplicatePhone(String phone);

    // User 회원가입
    User createUser(UserSignupReqDTO userSignupReqDTO, UserRole userRole);

    // 계정 상태 기반 사용자 조회
    User getByUserIdAndStatus(Long userId, UserStatus userStatus);

    // 비밀번호 확인
    void checkPassword(Long userId, String password);

    // 회원 탈퇴
    void deleteUser(Long userId, String password);

    // 사용자 ID 찾기
    Boolean findUserId(UserFindAccountReqDTO findAccountReqDTO);

    // 사용자 비밀번호 찾기
    String findUserPassword(UserFindAccountReqDTO findAccountReqDTO);

    // 사용자 비밀번호 수정
    void resetPassword(Long userId, UserResetPwdReqDTO resetReqDTO);
}