package com.kernel.admin.service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.info.AdminUserSearchInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminSearchRspDTO {

    // 관리자 ID
    private Long adminId;

    // 관리자 이름
    private String userName;

    // 관리자 전화번호
    private String phone;

    // 관리자 이메일
    private String email;

    // 관리자 상태
    private UserStatus status;

    // 관리자 생성일
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // info -> AdminSearchRspDTO 변환 메서드
    public static Page<AdminSearchRspDTO> fromInfo(Page<AdminUserSearchInfo> info) {
        return info.map(adminUserSearchInfo -> AdminSearchRspDTO.builder()
                .adminId(adminUserSearchInfo.getUserId())
                .userName(adminUserSearchInfo.getUserName())
                .phone(adminUserSearchInfo.getPhone())
                .email(adminUserSearchInfo.getEmail())
                .status(adminUserSearchInfo.getStatus())
                .createdAt(adminUserSearchInfo.getCreatedAt())
                .build());
    }
}
