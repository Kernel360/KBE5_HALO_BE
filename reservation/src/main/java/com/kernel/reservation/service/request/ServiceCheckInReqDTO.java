package com.kernel.reservation.service.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServiceCheckInReqDTO {

    // 체크인 첨부파일
    //@NotNull(message = "체크인 첨부파일은 필수입니다.")
    private Long inFileId;
}
