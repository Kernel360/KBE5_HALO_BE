package com.kernel.common.admin.dto.response;

import com.kernel.common.global.enums.UserStatus;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminManagerRspDTO {

    private Long managerId;
    private String userName;
    private String email;
    private String phone;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer reservationCount;
    private Integer reviewCount;
    private String bio;
    private Long profileImageId;
    //private List<AvailableAreaResponseDTO> availableArea;   // AvailableArea는 Manager 패키지에서 정의된 DTO라고 가정
    //private List<AvailableTimeResponseDTO> availableTime;   // AvailableTime는 Manager 패키지에서 정의된 DTO라고 가정
}
