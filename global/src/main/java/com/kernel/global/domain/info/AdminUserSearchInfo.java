package com.kernel.global.domain.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kernel.global.common.enums.UserStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminUserSearchInfo {
    private Long userId;
    private String userName;
    private String phone;
    private String email;
    private UserStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
