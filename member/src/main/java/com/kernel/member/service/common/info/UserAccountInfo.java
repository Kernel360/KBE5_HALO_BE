package com.kernel.member.service.common.info;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountInfo {

    // 핸드폰 번호
    private String phone;

    // 이메일
    private String email;

    // 이름
    private String userName;

    // 계정 상태
    private UserStatus status;

    // 생성일자
    private LocalDateTime createdAt;

    // 수정일자
    private LocalDateTime updatedAt;

    // User -> UserAccountInfo
    public static UserAccountInfo fromEntity(User user) {
        return UserAccountInfo.builder()
                .phone(user.getPhone())
                .email(user.getEmail())
                .userName(user.getUserName())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
