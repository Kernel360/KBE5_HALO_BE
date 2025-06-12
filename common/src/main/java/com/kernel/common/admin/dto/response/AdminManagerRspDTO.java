package com.kernel.common.admin.dto.response;

import com.kernel.common.global.enums.Gender;
import com.kernel.common.global.enums.UserStatus;

import com.kernel.common.manager.entity.AvailableTime;
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
    ///private List<AvailableAreaResponseDTO> availableArea;   // AvailableArea는 Manager 패키지에서 정의된 DTO라고 가정
    private List<AvailableTime> availableTimes;   // AvailableTime는 Manager 패키지에서 정의된 DTO라고 가정
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime contractAt;
    private LocalDateTime terminatedAt;
    private String terminationReason;

}
