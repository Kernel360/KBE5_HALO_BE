package com.kernel.member.service.common;

import com.kernel.global.common.enums.ErrorCode;
import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.common.exception.AuthException;
import com.kernel.global.domain.entity.User;
import com.kernel.member.common.exception.DuplicateUserException;
import com.kernel.member.repository.MemberUserRepository;
import com.kernel.member.service.common.request.UserFindAccountReqDTO;
import com.kernel.member.service.common.request.UserResetPwdReqDTO;
import com.kernel.member.service.common.request.UserSignupReqDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MemberUserRepository memberUserRepository;
    private final UserInfoService userInfoService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 사용자 아이디 찾기
     * @param findAccountReqDTO 아이디 찾기 요청 DTO
     * @return 수요자 계정 존재 여부
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean findUserId(UserFindAccountReqDTO findAccountReqDTO) {

        // 사용자 조회
        Optional<User> foundUser = memberUserRepository.findByPhoneAndUserNameAndStatus(
                        findAccountReqDTO.getPhone(),         // 핸드폰번호
                        findAccountReqDTO.getUserName(),      // 사용자 이름
                        UserStatus.ACTIVE                     // 계정 상태
        );

        // 사용자 없음 -> false
        if (foundUser.isEmpty())
            return false;

        // userId + 생년월일로 userInfo 조회
        return userInfoService.existByIdAndBirthDate(
                foundUser.get().getUserId(),
                findAccountReqDTO.getBirthDate());
    }

    /**
     * 사용자 비밀번호 찾기
     * @param findAccountReqDTO 비밀번호 찾기 요청 DTO
     * @return 랜덤 비밀번호
     */
    @Override
    @Transactional
    public String findUserPassword(UserFindAccountReqDTO findAccountReqDTO) {

        // 사용자 조회
        User foundUser = memberUserRepository.findByPhoneAndUserNameAndStatus(
                findAccountReqDTO.getPhone(),         // 핸드폰번호
                findAccountReqDTO.getUserName(),      // 사용자 이름
                UserStatus.ACTIVE                     // 계정 상태
        ).orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));


        // userId + 생년월일로 userInfo 조회
         userInfoService.existByIdAndBirthDate(
                foundUser.getUserId(),
                findAccountReqDTO.getBirthDate());

        // 랜덤 비밀번호 생성
        // TODO 종은님이 작성하신 랜덤비밀번호 사용할 예정
        String randomPassword = "test";

        // 비밀번호 암호화
        String afterRandomPassword = bCryptPasswordEncoder.encode(randomPassword);

        // 계정 비밀번호 재설정
        foundUser.resetPassword(afterRandomPassword);

        return randomPassword;
    }

    /**
     * 수요자 비밀번호 수정
     * @param userId 유저ID
     * @param resetReqDTO 새로운 비밀번호 요청 DTO
     */
    @Override
    @Transactional
    public void resetPassword(Long userId, UserResetPwdReqDTO resetReqDTO) {

        // 사용자 조회
        User foundUser = memberUserRepository.findByUserIdAndStatus(userId, UserStatus.ACTIVE)
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 일치 여부 확인
        validatePassword(resetReqDTO.getCurrentPassword(), foundUser.getPassword());

        // 비밀번호 암호화 후 수정
        foundUser.resetPassword(bCryptPasswordEncoder.encode(resetReqDTO.getNewPassword()));

    }
    /**
     * 중복 사용자 조회
     * @param phone 핸드폰 번호
     */
    @Override
    public void validateDuplicatePhone(String phone) {
        if(memberUserRepository.existsByPhone(phone)) {
            throw new DuplicateUserException();
        }
    }

    /**
     * 회원 탈퇴
     * @param userId 유저ID
     * @param password 확인용 비밀번호
     */
    @Override
    @Transactional
    public void deleteUser(Long userId, String password) {

        // User 조회
        User foundUser = memberUserRepository.findByUserIdAndStatus(userId, UserStatus.ACTIVE)
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 확인
        validatePassword(password, foundUser.getPassword());

        // 예약 확인
        //TODO 예약 내역 조회 개발 시 추가

        // 삭제
        foundUser.delete();
    }

    /**
     * 사용자 생성
     * @param reqDTO 회원생성요청 DTO
     * @param userRole User 권한
     */
    @Override
    public User createUser(UserSignupReqDTO reqDTO, UserRole userRole) {

        // Role 설정, 비밀번호 암호화 후 User 저장
        return memberUserRepository.save(reqDTO.toEntityWithRole(userRole, bCryptPasswordEncoder));
    }

    /**
     * id 및 계정 상태 기반 사용자 찾기
     * @param userId 유저ID
     * @param userStatus 계정 상태
     * @return 조회된 User
     */
    @Override
    public User getByUserIdAndStatus(Long userId, UserStatus userStatus) {
        return memberUserRepository.findByUserIdAndStatus(userId, userStatus)
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 비밀번호 확인
     * @param userId 유저ID
     * @param password 확인용 비밀번호
     */
    @Override
    public void checkPassword(Long userId, String password) {

        // User 조회
        User foundUser = memberUserRepository.findByUserIdAndStatus(userId, UserStatus.ACTIVE)
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 확인
        validatePassword(password, foundUser.getPassword());
    }

    /**
     * 비밀번호 일치 여부 확인
     * @param reqPassword 조회한 수요자 비밀번호
     * @param entityPassword 요청한 수요자 비밀번호
     */
    private void validatePassword(String reqPassword, String entityPassword) {
        if(!bCryptPasswordEncoder.matches(reqPassword, entityPassword))
            throw new AuthException(ErrorCode.INVALID_PASSWORD);
    }
}
