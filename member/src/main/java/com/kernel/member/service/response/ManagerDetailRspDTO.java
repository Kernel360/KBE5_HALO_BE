package com.kernel.member.service.response;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.common.enums.Gender;
import com.kernel.member.service.common.info.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "매니저 상세 응답 DTO")
public class ManagerDetailRspDTO {

    /* User */
    @Schema(description = "연락처(=계정ID)", example = "010-1234-5678", required = true)
    private String phone;

    @Schema(description = "이메일", example = "example@email.com", required = true)
    private String email;

    @Schema(description = "이름", example = "홍길동", required = true)
    private String userName;

    /* UserInfo */
    @Schema(description = "생년월일", example = "1990-01-01", required = true)
    private LocalDate birthDate;

    @Schema(description = "성별", example = "MALE", required = true)
    private Gender gender;

    @Schema(description = "위도", example = "37.5665", required = true)
    private BigDecimal latitude;

    @Schema(description = "경도", example = "126.9780", required = true)
    private BigDecimal longitude;

    @Schema(description = "도로명 주소", example = "서울특별시 중구 세종대로 110", required = true)
    private String roadAddress;

    @Schema(description = "상세 주소", example = "10층", required = true)
    private String detailAddress;

    /* Manager */
    @Schema(description = "한줄소개", example = "안녕하세요, 매니저입니다.", required = true)
    private String bio;

    @Schema(description = "프로필 이미지 경로", example = "/images/profile.jpg", required = false)
    private String profileImagePath;

    @Schema(description = "첨부파일 경로", example = "/files/document.pdf", required = false)
    private String filePaths;

    @Schema(description = "계정 상태", example = "ACTIVE", required = true)
    private UserStatus status;

    @Schema(description = "계약 일시", example = "2023-01-01T10:00:00", required = true)
    private LocalDateTime contractAt;

    @Schema(description = "업무 가능 시간 목록", required = true)
    private List<AvailableTimeInfo> availableTimes;

    @Schema(description = "계약 해지 요청 일시", example = "2023-01-10T15:00:00", required = false)
    private LocalDateTime requestedAt;

    @Schema(description = "계약 해지 요청 사유", example = "개인 사유", required = false)
    private String terminationReason;

    @Schema(description = "계약 해지 일시", example = "2023-01-15T10:00:00", required = false)
    private LocalDateTime terminatedAt;

    @Schema(description = "Info 객체를 DTO로 변환")
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
                .contractAt(managerDetailInfo.getContractDate() != null ? managerDetailInfo.getContractDate() : LocalDateTime.now())
                .availableTimes(availableTimes)
                .requestedAt(managerTerminationInfo.getRequestAt() != null ? managerTerminationInfo.getRequestAt() : null)
                .terminationReason(managerTerminationInfo.getTerminationReason() != null ? managerTerminationInfo.getTerminationReason() : "")
                .terminatedAt(managerTerminationInfo.getTerminatedAt() != null ? managerTerminationInfo.getTerminatedAt() : null)
                .build();
    }
}