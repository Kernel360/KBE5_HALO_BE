package com.kernel.member.service.request;

import com.kernel.member.domain.entity.Manager;
import com.kernel.member.domain.entity.ManagerTermination;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "매니저 계약 해지 요청 DTO")
public class ManagerTerminationReqDTO {

    @Schema(description = "계약 해지 사유", example = "업무 성과 부족으로 계약 해지 요청", required = true, minLength = 10, maxLength = 500)
    @NotBlank(message = "계약 해지 사유는 필수입니다.")
    @Size(min = 10, max = 500, message = "계약 해지 사유는 10자 이상 500자 이하 입니다.")
    private String terminationReason;

    @Schema(description = "DTO를 엔티티로 변환하는 메서드")
    public static ManagerTermination toEntity(Manager manager, ManagerTerminationReqDTO request) {
        return ManagerTermination.builder()
                .manager(manager)
                .reason(request.getTerminationReason())
                .build();
    }
}