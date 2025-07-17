package com.kernel.member.service;

import com.kernel.global.common.enums.ErrorCode;
import com.kernel.global.common.enums.UserRole;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.common.exception.AuthException;
import com.kernel.global.domain.entity.File;
import com.kernel.global.domain.entity.User;
import com.kernel.global.repository.FileRepository;
import com.kernel.member.common.enums.MemberErrorCode;
import com.kernel.member.common.exception.AvailableTimeException;
import com.kernel.member.common.exception.SpecialtyException;
import com.kernel.member.domain.entity.AvailableTime;
import com.kernel.member.domain.entity.Manager;
import com.kernel.member.domain.entity.ManagerTermination;
import com.kernel.member.domain.entity.UserInfo;
import com.kernel.member.domain.entity.*;
import com.kernel.member.repository.AvailableTimeRepository;
import com.kernel.member.repository.ManagerRepository;
import com.kernel.member.repository.ManagerStatisticRepository;
import com.kernel.member.repository.ManagerTerminationRepository;
import com.kernel.member.repository.common.ManagerServiceCategoryRepository;
import com.kernel.member.service.common.UserInfoService;
import com.kernel.member.service.common.UserService;
import com.kernel.member.service.common.info.*;
import com.kernel.member.service.request.ManagerSignupReqDTO;
import com.kernel.member.service.request.ManagerUpdateReqDTO;
import com.kernel.member.service.response.ManagerDetailRspDTO;

import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final UserService userService;
    private final UserInfoService userInfoService;
    private final ManagerRepository managerRepository;
    private final AvailableTimeRepository availableTimeRepository;
    private final ManagerTerminationRepository managerTerminationRepository;
    private final FileRepository fileRepository;
    private final ManagerServiceCategoryRepository managerServiceCategoryRepository;
    private final ManagerStatisticRepository managerStatisticRepository;

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

        // 4. Manager 저장
        File file = fileRepository.findByFileId(signupReqDTO.getManagerReqDTO().getFileId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 파일입니다."));
        File profileFile = fileRepository.findByFileId(signupReqDTO.getManagerReqDTO().getProfileImageFileId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 프로필 이미지 파일입니다."));

        ServiceCategory serviceCategory = managerServiceCategoryRepository.findById(signupReqDTO.getManagerReqDTO().getSpecialty())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 서비스 카테고리입니다."));

        Manager savedManager = signupReqDTO.getManagerReqDTO().toEntity(signupReqDTO.getManagerReqDTO(), savedUser, file, profileFile, serviceCategory);
        savedManager.apply();

        managerRepository.save(savedManager);

        // 5. AvailableTime 저장
        List<AvailableTime> availableTimeList = signupReqDTO.toEntityList(signupReqDTO.getAvailableTimeReqDTOList(), savedManager);
        availableTimeRepository.saveAll(availableTimeList);

        // 6. ManagerStatistic entity 생성
        ManagerStatistic managerStatistic = ManagerStatistic.builder()
                .user(savedUser)
                .reservationCount(0)
                .reviewCount(0)
                .averageRating(BigDecimal.valueOf(0.0))
                .build();

        managerStatisticRepository.save(managerStatistic);

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
        List<AvailableTime> foundAvailableTimeList = availableTimeRepository.findByManager(foundManager);

        // 5. ManagerTermination 조회
        ManagerTermination foundManagerTermination = managerTerminationRepository.findByManager(foundManager)
                .orElse(null);

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
    public ManagerDetailRspDTO updateManager(Long userId, ManagerUpdateReqDTO updateReqDTO) {

        // 1. User 조회
        User foundUser = userService.getByUserIdAndStatus(userId, UserStatus.ACTIVE);

        // 2. UserInfo 조회
        UserInfo foundUserInfo = userInfoService.getUserById(foundUser.getUserId());

        // 3. Manager 조회
        Manager foundManager = managerRepository.findById(foundUser.getUserId())
                .orElseThrow(()-> new AuthException(ErrorCode.USER_NOT_FOUND));

        // 4. Available Time 조회
        List<AvailableTime> foundAvailableTimeList = availableTimeRepository.findByManager(foundManager);
        if (foundAvailableTimeList.isEmpty()) {
            throw new AvailableTimeException(MemberErrorCode.AVAILABLE_TIME_NOT_FOUND);
        }

        // 5. ManagerTermination 조회
        ManagerTermination foundManagerTermination = managerTerminationRepository.findByManager(foundManager)
                .orElse(null);

        // 6. User 수정
        foundUser.updateEmail(updateReqDTO.getUserUpdateReqDTO().getEmail());

        // 7. UserInfo 수정
        foundUserInfo.updateAddress(
                updateReqDTO.getUserInfoUpdateReqDTO().getRoadAddress(),
                updateReqDTO.getUserInfoUpdateReqDTO().getDetailAddress(),
                updateReqDTO.getUserInfoUpdateReqDTO().getLatitude(),
                updateReqDTO.getUserInfoUpdateReqDTO().getLongitude()
        );

        // 8. Manager 수정
        ServiceCategory serviceCategory = managerServiceCategoryRepository.findById(updateReqDTO.getManagerUpdateInfoReqDTO().getSpecialty())
                .orElseThrow(() -> new SpecialtyException(MemberErrorCode.SPECIALTY_NOT_FOUND));
        foundManager.update(updateReqDTO.getManagerUpdateInfoReqDTO(), serviceCategory);

        // 9. Available Time 수정
        availableTimeRepository.deleteAll(foundAvailableTimeList);
        List<AvailableTime> updatedAvailableTimeList = updateReqDTO.toEntityList(updateReqDTO.getAvailableTimeUpdateReqDTOList(), foundManager);
        availableTimeRepository.saveAll(updatedAvailableTimeList);

        // 10. DTO 변환 후 return
        return ManagerDetailRspDTO.fromInfos(
                UserAccountInfo.fromEntity(foundUser),
                UserDetailInfo.fromEntity(foundUserInfo),
                ManagerDetailInfo.fromEntity(foundManager),
                AvailableTimeInfo.fromEntityList(updatedAvailableTimeList),
                ManagerTerminationInfo.fromEntity(foundManagerTermination)
        );
    }
}
