package com.kernel.admin.service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.info.AdminUserSearchInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "관리자 검색 응답 DTO")
public class AdminSearchRspDTO {

    @Schema(description = "관리자 ID", example = "1")
    private Long adminId;

    @Schema(description = "관리자 이름", example = "홍길동")
    private String userName;

    @Schema(description = "관리자 전화번호", example = "01012345678")
    private String phone;

    @Schema(description = "관리자 이메일", example = "example@domain.com")
    private String email;

    @Schema(description = "관리자 상태", example = "ACTIVE")
    private UserStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "관리자 생성일", example = "2023-01-01 12:00:00")
    private LocalDateTime createdAt;

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
