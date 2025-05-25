package com.kernel.admin.dto.request;

import com.kernel.manager.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminManagerStatusRequestDTO {
    // TODO: Request로 받을 필드 정의
    private Status status;  // Manager 모듈에서 Status는 enum으로 정의되어 있다고 가정
}
