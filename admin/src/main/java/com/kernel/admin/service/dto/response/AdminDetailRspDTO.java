package com.kernel.admin.service.dto.response;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.service.common.info.UserAccountInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminDetailRspDTO {
    // 핸드폰 번호
    private String phone;

    // 이메일
    private String email;

    // 이름
    private String userName;

    // 계정 상태
    private UserStatus status;

    // info -> DTO 변환
    public static AdminDetailRspDTO fromInfo(UserAccountInfo info) {
        return AdminDetailRspDTO.builder()
                .phone(info.getPhone())
                .email(info.getEmail())
                .userName(info.getUserName())
                .status(info.getStatus())
                .build();
    }
}
