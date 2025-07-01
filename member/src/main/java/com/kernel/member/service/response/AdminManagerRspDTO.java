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
    private Long profileImageId;
    private Long fileId;  // 첨부파일 ID
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
                .managerId(info.getManagerId())
                .userName(info.getUserName())
                .birthDate(info.getBirthDate() != null ? LocalDate.parse(info.getBirthDate()) : null)
                .gender(info.getGender())
                .email(info.getEmail())
                .phone(info.getPhone())
                .roadAddress(info.getRoadAddress())
                .detailAddress(info.getDetailAddress())
                .status(info.getStatus())
                .reservationCount(info.getReservationCount())
                .reviewCount(info.getReviewCount())
                .averageRating(info.getRating() != null ? BigDecimal.valueOf(info.getRating()) : null)
                .bio(info.getBio())
                .profileImageId(info.getProfileImageId())
                .fileId(info.getFileId())
                .availableTimes(availableTimes)
                .createdAt(info.getCreatedAt())
                .updatedAt(info.getUpdatedAt())
                .contractAt(info.getContractDate())
                .terminatedAt(info.getTerminatedAt())
                .terminationReason(info.getReason())
                .build();
    }

}
