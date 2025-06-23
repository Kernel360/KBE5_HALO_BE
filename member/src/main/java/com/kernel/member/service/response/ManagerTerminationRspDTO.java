package com.kernel.member.service.response;

import com.kernel.member.service.common.info.ManagerTerminationInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ManagerTerminationRspDTO {

    // 계약 해지 요청 일시
    private LocalDateTime requestedAt;

    // 계약 해지 일시
    private LocalDateTime terminatedAt;

    // 계약 해지 사유
    private String TerminationReason;

    // Info -> ManagerTerminationRspDTO 변환 메서드
    public static ManagerTerminationRspDTO fromInfo(ManagerTerminationInfo info) {
        return ManagerTerminationRspDTO.builder()
                .requestedAt(info.getRequestAt())
                .terminatedAt(info.getTerminatedAt())
                .TerminationReason(info.getTerminationReason())
                .build();
    }

}
