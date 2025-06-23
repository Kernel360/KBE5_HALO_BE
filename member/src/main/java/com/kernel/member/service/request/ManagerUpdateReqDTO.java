package com.kernel.member.service.request;

import com.kernel.member.domain.entity.AvailableTime;
import com.kernel.member.service.common.request.UserInfoUpdateReqDTO;
import com.kernel.member.service.common.request.UserUpdateReqDTO;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ManagerUpdateReqDTO {

    // User
    @Valid
    private UserUpdateReqDTO userUpdateReqDTO;

    // UserInfo
    @Valid
    private UserInfoUpdateReqDTO userInfoUpdateReqDTO;

    // Manager
    @Valid
    private ManagerUpdateInfoReqDTO managerUpdateInfoReqDTO;

    // Available Time
    @Valid
    private List<AvailableTimeUpdateReqDTO> availableTimeUpdateReqDTOList;

    // List<AvailableTimeUpdateReqDTO> -> List<AvailableTime>로 매핑
    @Builder
    public static List<AvailableTime> toEntityList(List<AvailableTimeUpdateReqDTO> availableTimeReqDTOList) {
        return availableTimeReqDTOList.stream()
                .map(AvailableTimeUpdateReqDTO::toEntity)
                .toList();
    }

}
