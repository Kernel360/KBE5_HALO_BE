package com.kernel.admin.dto.response;

import com.kernel.manager.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminManagerStatusResponseDTO {
    // TODO: Response로 반환할 필드 정의
    private Long managerId;
    private Status status;  // Manager 모듈에서 Status는 enum으로 정의되어 있다고 가정
    private LocalDateTime updatedAt;
    private Long updatedBy; // 업데이트한 관리자 ID
}
