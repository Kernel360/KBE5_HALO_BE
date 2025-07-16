package com.kernel.member.service.response;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.common.enums.Gender;

import com.kernel.member.service.common.info.AdminManagerDetailInfo;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminManagerRspDTO {

    private Long managerId;
    private String userName;
    private LocalDate birthDate;
    private Gender gender;
    private String email;
    private String phone;
    private String roadAddress;
    private String detailAddress;
    private UserStatus status;
    private Integer reservationCount;
    private Integer reviewCount;
    private BigDecimal averageRating;
    private String bio;
    private String profileImagePath;
    private String filePaths;
    ///private List<AvailableAreaResponseDTO> availableArea;
    private List<AvailableTimeRspDTO> availableTimes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime contractAt;
    private LocalDateTime terminatedAt;
    private String terminationReason;

    // AdminManagerDetailInfo -> AdminManagerRspDTO
    public static AdminManagerRspDTO fromInfo(AdminManagerDetailInfo info, List<AvailableTimeRspDTO> availableTimes) {
        return AdminManagerRspDTO.builder()
                .managerId(info.getUserId())
                .userName(info.getUserName())
                .birthDate(info.getBirthDate() != null ? info.getBirthDate() : null)
                .gender(info.getGender())
                .email(info.getEmail())
                .phone(info.getPhone())
                .roadAddress(info.getRoadAddress())
                .detailAddress(info.getDetailAddress())
                .status(info.getStatus())
                .reservationCount(info.getReservationCount())
                .reviewCount(info.getReviewCount())
                .averageRating(info.getAverageRating())
                .bio(info.getBio())
                .profileImagePath(info.getProfileImagePath() != null ? info.getProfileImagePath() : null)
                .filePaths(info.getFilePaths() != null ? info.getFilePaths() : null)
                .availableTimes(availableTimes)
                .createdAt(info.getCreatedAt())
                .updatedAt(info.getUpdatedAt())
                .contractAt(info.getContractDate())
                .terminatedAt(info.getTerminatedAt())
                .terminationReason(info.getReason())
                .build();
    }

}
