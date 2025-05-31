package com.kernel.common.manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ManagerTerminationReqDTO {

    @NotBlank(message = "계약 해지 사유는 필수 입력입니다.")
    @Size(max = 500)
    private String terminationReason;
}
