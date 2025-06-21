package com.kernel.member.service.common;

import com.kernel.global.domain.entity.User;
import com.kernel.member.domain.entity.UserInfo;
import com.kernel.member.service.common.request.UserInfoSignupReqDTO;

import java.time.LocalDate;

public interface UserInfoService {

    // UserInfo 조회
    UserInfo getUserById(Long userId);

    // UserInfo 저장
    UserInfo createUserInfo(UserInfoSignupReqDTO userInfoSignupReqDTO, User user);

    // userId, 생년월일 기반 UserInfo 조회 for Id 찾기
    Boolean existByIdAndBirthDate(Long userId, LocalDate birthDate);

}
