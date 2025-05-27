package com.kernel.common.admin.dto.response;


import com.kernel.common.admin.entity.Status;   // 추후에 Status entity가 정의된 위치로 변경 필요

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerResponseDTO {
    // TODO: Response로 반환할 필드 정의

    private Long managerId;
    private String userName;
    private String email;
    private String phone;
    private Status status;  // manager 패키지나 global 패키지에서 Status는 enum으로 정의되어 있다고 가정
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer reservationCount;
    private String bio;
    private Long profileImageId;
    //private List<AvailableAreaResponseDTO> availableArea;   // AvailableArea는 Manager 패키지에서 정의된 DTO라고 가정
    //private List<AvailableTimeResponseDTO> availableTime;   // AvailableTime는 Manager 패키지에서 정의된 DTO라고 가정
}
