package com.kernel.member.service.request;

import com.kernel.member.domain.entity.AvailableTime;
import com.kernel.member.service.common.request.UserInfoSignupReqDTO;
import com.kernel.member.service.common.request.UserSignupReqDTO;

import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class ManagerSignupReqDTO {

    // User
    @Valid
    private UserSignupReqDTO userSignupReqDTO;

    // UserInfo
    @Valid
    private UserInfoSignupReqDTO userInfoSignupReqDTO;

    // Manager
    @Valid
    private ManagerReqDTO managerReqDTO;

    // Available Time
    @Valid
    private List<AvailableTimeReqDTO> availableTimeReqDTOList;

    // AvailableTimeReqDTO -> List<AvailableTime>로 매핑
    @Builder
    public static List<AvailableTime> toEntityList(List<AvailableTimeReqDTO> availableTimeReqDTOList) {
        return availableTimeReqDTOList.stream()
                .map(AvailableTimeReqDTO::toEntity)
                .toList();
    }
}
