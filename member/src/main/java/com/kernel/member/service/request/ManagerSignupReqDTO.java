package com.kernel.member.service.request;

import com.kernel.member.domain.entity.AvailableTime;
import com.kernel.member.service.common.request.UserInfoSignupReqDTO;
import com.kernel.member.service.common.request.UserSignupReqDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "매니저 회원가입 요청 DTO")
public class ManagerSignupReqDTO {

    @Schema(description = "사용자 회원가입 정보", required = true)
    @Valid
    private UserSignupReqDTO userSignupReqDTO;

    @Schema(description = "사용자 추가 정보", required = true)
    @Valid
    private UserInfoSignupReqDTO userInfoSignupReqDTO;

    @Schema(description = "매니저 정보", required = true)
    @Valid
    private ManagerReqDTO managerReqDTO;

    @Schema(description = "가능 시간 목록", required = true)
    @Valid
    private List<AvailableTimeReqDTO> availableTimeReqDTOList;

    @Builder
    @Schema(description = "가능 시간 목록을 엔티티 리스트로 변환")
    public static List<AvailableTime> toEntityList(List<AvailableTimeReqDTO> availableTimeReqDTOList) {
        return availableTimeReqDTOList.stream()
                .map(AvailableTimeReqDTO::toEntity)
                .toList();
    }
}