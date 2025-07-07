package com.kernel.member.service.request;

import com.kernel.member.domain.entity.AvailableTime;
import com.kernel.member.domain.entity.Manager;
import com.kernel.member.service.common.request.UserInfoUpdateReqDTO;
import com.kernel.member.service.common.request.UserUpdateReqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "매니저 정보 수정 요청 DTO")
public class ManagerUpdateReqDTO {

    @Schema(description = "사용자 수정 정보", required = true)
    @Valid
    private UserUpdateReqDTO userUpdateReqDTO;

    @Schema(description = "사용자 추가 수정 정보", required = true)
    @Valid
    private UserInfoUpdateReqDTO userInfoUpdateReqDTO;

    @Schema(description = "매니저 수정 정보", required = true)
    @Valid
    private ManagerUpdateInfoReqDTO managerUpdateInfoReqDTO;

    @Schema(description = "가능 시간 수정 목록", required = true)
    @Valid
    private List<AvailableTimeUpdateReqDTO> availableTimeUpdateReqDTOList;

    @Builder
    @Schema(description = "가능 시간 목록을 엔티티 리스트로 변환")
    public static List<AvailableTime> toEntityList(List<AvailableTimeUpdateReqDTO> availableTimeReqDTOList, Manager manager) {
        return availableTimeReqDTOList.stream()
                .map(availableTimeUpdateReqDTO -> AvailableTimeUpdateReqDTO.toEntity(availableTimeUpdateReqDTO, manager))
                .toList();
    }
}