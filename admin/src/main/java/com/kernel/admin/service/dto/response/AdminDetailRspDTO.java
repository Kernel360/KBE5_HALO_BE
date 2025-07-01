package com.kernel.admin.service.dto.response;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.member.service.common.info.UserAccountInfo;
import lombok.Builder;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Builder
@Schema(description = "관리자 상세 응답 DTO")
public class AdminDetailRspDTO {

    @Schema(description = "핸드폰 번호", example = "01012345678")
    private String phone;

    @Schema(description = "이메일 주소", example = "example@domain.com")
    private String email;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String userName;

    @Schema(description = "계정 상태", example = "ACTIVE")
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