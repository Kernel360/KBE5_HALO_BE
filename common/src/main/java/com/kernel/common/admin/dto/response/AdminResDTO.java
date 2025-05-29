package com.kernel.common.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResDTO {
    private String id;  // 일반적으로 id는 Long 타입이지만 해당 id는 실제 로그인을 위한 식별자로 사용되므로 String 타입으로 설정
    private Long createdBy;
    private LocalDateTime createdAt;
}
