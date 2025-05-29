package com.kernel.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCreateRequestDTO {
    // TODO: 관리자 계정을 생성할 때 요청 DTO를 정의
    private String id;
    private String password;
}
