package com.kernel.member.service;

import com.kernel.global.common.enums.ErrorCode;
import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.common.exception.AuthException;
import com.kernel.global.domain.entity.File;
import com.kernel.global.domain.entity.User;
import com.kernel.global.repository.FileRepository;
import com.kernel.member.domain.entity.AvailableTime;
import com.kernel.member.domain.entity.Manager;
import com.kernel.member.domain.entity.ManagerTermination;
import com.kernel.member.domain.entity.UserInfo;
import com.kernel.member.repository.AvailableTimeRepository;
import com.kernel.member.repository.ManagerRepository;
import com.kernel.member.repository.ManagerTerminationRepository;
import com.kernel.member.service.common.UserInfoService;
import com.kernel.member.service.common.UserService;
import com.kernel.member.service.common.info.*;
import com.kernel.member.service.request.ManagerSignupReqDTO;
import com.kernel.member.service.request.ManagerUpdateReqDTO;
import com.kernel.member.service.response.ManagerDetailRspDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final UserService userService;
    private final UserInfoService userInfoService;
    private final ManagerRepository managerRepository;
    private final AvailableTimeRepository availableTImeRepository;
    private final ManagerTerminationRepository managerTerminationRepository;
    private final FileRepository fileRepository;

    /**
     * 매니저 회원가입
     *
     * @param signupReqDTO 매니저 회원가입 요청 DTO
     */
    @Override
    @Transactional
    public void signup(ManagerSignupReqDTO signupReqDTO) {
        // 1. phone 중복 검사
        userService.validateDuplicatePhone(signupReqDTO.getUserSignupReqDTO().getPhone());

        // 2. user 저장
        User savedUser = userService.createUser(signupReqDTO.getUserSignupReqDTO(), UserRole.MANAGER);

        // 3. userInfo 저장
        UserInfo savedUserInfo = userInfoService.createUserInfo(signupReqDTO.getUserInfoSignupReqDTO(), savedUser);

        // 4. AvailableTime 저장
        List<AvailableTime> availableTimeList = signupReqDTO.toEntityList(signupReqDTO.getAvailableTimeReqDTOList());
        availableTImeRepository.saveAll(availableTimeList);

        // 5. Manager 저장
        File file = fileRepository.findByFileId(signupReqDTO.getManagerReqDTO().getFileId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 파일입니다."));
        File profileFile = fileRepository.findByFileId(signupReqDTO.getManagerReqDTO().getProfileImageFileId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 프로필 이미지 파일입니다."));

        managerRepository.save(signupReqDTO.getManagerReqDTO().toEntity(signupReqDTO.getManagerReqDTO(), savedUser, file, profileFile));
    }

    /**
     * 매니저 정보 조회
     *
     * @param userId 매니저 ID
     * @return 매니저 상세 정보 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ManagerDetailRspDTO getManager(Long userId) {
        // 1. User 조회
        User foundUser = userService.getByUserIdAndStatus(userId, UserStatus.ACTIVE);

        // 2. UserInfo 조회
        UserInfo foundUserInfo = userInfoService.getUserById(foundUser.getUserId());

        // 3. Manager 조회
        Manager foundManager = managerRepository.findById(foundUser.getUserId())
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        // 4. Available Time 조회
        List<AvailableTime> foundAvailableTimeList = availableTImeRepository.findByManager(foundManager);

        // 5. ManagerTermination 조회
        ManagerTermination foundManagerTermination = managerTerminationRepository.findByManager(foundManager)
                .orElseThrow(() -> new NoSuchElementException("매니저 해지 정보가 존재하지 않습니다."));

        // 5. 응답 DTO 생성 및 반환
        return ManagerDetailRspDTO.fromInfos(
                UserAccountInfo.fromEntity(foundUser),
                UserDetailInfo.fromEntity(foundUserInfo),
                ManagerDetailInfo.fromEntity(foundManager),
                AvailableTimeInfo.fromEntityList(foundAvailableTimeList),
                ManagerTerminationInfo.fromEntity(foundManagerTermination)
        );
    }

    /**
     * 매니저 정보 수정
     *
     * @param userId 매니저 ID
     * @param updateReqDTO 매니저 정보 수정 요청 DTO
     * @return 수정된 매니저 상세 정보 DTO
     */
    @Override
    @Transactional
    public ManagerDetailRspDTO updateManager(Long userId,ManagerUpdateReqDTO updateReqDTO) {

        // User 조회
        User foundUser = userService.getByUserIdAndStatus(userId, UserStatus.ACTIVE);

        // UserInfo 조회
        UserInfo foundUserInfo = userInfoService.getUserById(foundUser.getUserId());

        // Manager 조회
        Manager foundManager = managerRepository.findById(foundUser.getUserId())
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        // Available Time 조회
        List<AvailableTime> foundAvailableTimeList = availableTImeRepository.findByManager(foundManager);

        // ManagerTermination 조회
        ManagerTermination foundManagerTermination = managerTerminationRepository.findByManager(foundManager)
                .orElseThrow(() -> new NoSuchElementException("매니저 해지 정보가 존재하지 않습니다."));

        // User 수정
        foundUser.updateEmail(updateReqDTO.getUserUpdateReqDTO().getEmail());

        // UserInfo 수정
        foundUserInfo.updateAddress(
                updateReqDTO.getUserInfoUpdateReqDTO().getRoadAddress(),
                updateReqDTO.getUserInfoUpdateReqDTO().getDetailAddress(),
                updateReqDTO.getUserInfoUpdateReqDTO().getLatitude(),
                updateReqDTO.getUserInfoUpdateReqDTO().getLongitude()
        );

        // Manager 수정
        foundManager.update(updateReqDTO.getManagerUpdateInfoReqDTO());

        // Available Time 수정
        availableTImeRepository.deleteAll(foundAvailableTimeList);
        List<AvailableTime> updatedAvailableTimeList = updateReqDTO.toEntityList(updateReqDTO.getAvailableTimeUpdateReqDTOList());
        availableTImeRepository.saveAll(updatedAvailableTimeList);

        // DTO 변환 후 return
        return ManagerDetailRspDTO.fromInfos(
                UserAccountInfo.fromEntity(foundUser),
                UserDetailInfo.fromEntity(foundUserInfo),
                ManagerDetailInfo.fromEntity(foundManager),
                AvailableTimeInfo.fromEntityList(updatedAvailableTimeList),
                ManagerTerminationInfo.fromEntity(foundManagerTermination)
        );
    }
}
