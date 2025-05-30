package com.kernel.common.admin.dto.response;


import com.kernel.common.global.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerRspDTO {
    // TODO: Response로 반환할 필드 정의

    private Long managerId;
    private String userName;
    private String email;
    private String phone;
    private UserStatus status;  // manager 패키지나 global 패키지에서 Status는 enum으로 정의되어 있다고 가정
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer reservationCount;
    private Integer reviewCount;
    private String bio;
    private Long profileImageId;
    //private List<AvailableAreaResponseDTO> availableArea;   // AvailableArea는 Manager 패키지에서 정의된 DTO라고 가정
    //private List<AvailableTimeResponseDTO> availableTime;   // AvailableTime는 Manager 패키지에서 정의된 DTO라고 가정
}
