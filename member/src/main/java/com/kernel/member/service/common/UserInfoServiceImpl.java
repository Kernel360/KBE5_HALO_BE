package com.kernel.member.service.common;

import com.kernel.global.domain.entity.User;
import com.kernel.member.domain.entity.UserInfo;
import com.kernel.member.repository.UserInfoRepository;
import com.kernel.member.service.common.request.UserInfoSignupReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    /**
     * 사용자 생성
     * @param reqDTO 회원생성요청 DTO
     */
    @Override
    public UserInfo createUserInfo(UserInfoSignupReqDTO reqDTO, User user) {

        // UserInfo 저장
        return userInfoRepository.save(reqDTO.toEntity(user));

    }

    /**
     * userInfo 조회
     * @param userId 조회 요청 user의 infoId
     */
    @Override
    public UserInfo getUserById(Long userId) {

        // UserInfo 조회
        return userInfoRepository.findById(userId)
               .orElseThrow( () -> new NoSuchElementException("존재하지 않는 사용자 입니다."));
    }

    /**
     * userId, 생년월일 기반 UserInfo 조회 for Id 찾기
     * @param userId 사용자 ID
     * @param birthDate 생년월일
     */
    @Override
    public Boolean existByIdAndBirthDate(Long userId, LocalDate birthDate) {
        return userInfoRepository.existsByUserIdAndBirthDate(userId, birthDate);
    }
}
