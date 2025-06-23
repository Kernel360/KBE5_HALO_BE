package com.kernel.member.service.response;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.common.enums.Gender;

import com.kernel.member.service.common.info.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDetailRspDTO {

    /* User */
    // 연락처(=계정ID)
    private String phone;

    // 이메일
    private String email;

    // 이름
    private String userName;

    /* UserInfo */
    // 생년월일
    private LocalDate birthDate;

    // 성별
    private Gender gender;

    // 위도
    private BigDecimal latitude;

    // 경도
    private BigDecimal longitude;

    // 도로명 주소
    private String roadAddress;

    // 상세 주소
    private String detailAddress;

    /* Manager */
    // 한줄소개
    private String bio;

    // 프로필이미지 경로
    private String profileImagePath;

    // 첨부파일 경로
    private String filePaths;

    // 계정 상태
    private UserStatus status;

    // 계약일시
    private LocalDateTime contractAt;

    // 업무가능시간
    private List<AvailableTimeInfo> availableTimes;

    // 계약 해지 요청 일시
    private LocalDateTime requestedAt;

    // 계약 해지 요청 사유
    private String terminationReason;

    // 계약 해지 일시
    private LocalDateTime terminatedAt;

    // Info -> DTO 변환
    public static ManagerDetailRspDTO fromInfos(
            UserAccountInfo userAccountInfo,
            UserDetailInfo userDetailInfo,
            ManagerDetailInfo managerDetailInfo,
            List<AvailableTimeInfo> availableTimes,
            ManagerTerminationInfo managerTerminationInfo
    ) {
        return ManagerDetailRspDTO.builder()
                .phone(userAccountInfo.getPhone())
                .email(userAccountInfo.getEmail())
                .userName(userAccountInfo.getUserName())
                .birthDate(userDetailInfo.getBirthDate())
                .gender(userDetailInfo.getGender())
                .latitude(userDetailInfo.getLatitude())
                .longitude(userDetailInfo.getLongitude())
                .roadAddress(userDetailInfo.getRoadAddress())
                .detailAddress(userDetailInfo.getDetailAddress())
                .bio(managerDetailInfo.getBio())
                .profileImagePath(managerDetailInfo.getProfileImageFilePath() != null ? managerDetailInfo.getProfileImageFilePath() : "")
                .filePaths(managerDetailInfo.getFilePaths() != null ? managerDetailInfo.getFilePaths() : "")
                .status(userAccountInfo.getStatus())
                .contractAt(managerDetailInfo.getContractDate())
                .availableTimes(availableTimes)
                .requestedAt(managerTerminationInfo.getRequestAt())
                .terminationReason(managerTerminationInfo.getTerminationReason())
                .terminatedAt(managerTerminationInfo.getTerminatedAt())
                .build();
    }
}
