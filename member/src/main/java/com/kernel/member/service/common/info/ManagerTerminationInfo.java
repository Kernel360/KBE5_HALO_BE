package com.kernel.member.service.common.info;

import com.kernel.member.domain.entity.ManagerTermination;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ManagerTerminationInfo {
    private LocalDateTime requestAt;
    private LocalDateTime terminatedAt;
    private String TerminationReason;

    // Entity -> Info 변환 메서드
    public static ManagerTerminationInfo fromEntity(ManagerTermination entity) {
        return ManagerTerminationInfo.builder()
                .requestAt(entity.getRequestAt())
                .terminatedAt(entity.getTerminatedAt())
                .TerminationReason(entity.getReason())
                .build();
    }

}
