package com.kernel.member.service.request;

import com.kernel.member.domain.entity.Manager;
import com.kernel.member.domain.entity.ManagerTermination;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ManagerTerminationReqDTO {

    // 계약 해지 사유
    @NotBlank(message = "계약 해지 사유는 필수입니다.")
    @Size(min = 10, max = 500, message = "계약 해지 사유는 10자 이상 500자 이하 입니다.")
    private String terminationReason;

    // ManagerTerminationReqDTO -> Entity 변환 메서드
    public static ManagerTermination toEntity(Manager manager, ManagerTerminationReqDTO request) {
        return ManagerTermination.builder()
                .manager(manager)
                .reason(request.getTerminationReason())
                .build();
    }
}
