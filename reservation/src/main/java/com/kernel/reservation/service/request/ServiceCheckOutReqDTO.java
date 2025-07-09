package com.kernel.reservation.service.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ServiceCheckOutReqDTO {

    // 체크아웃 첨부파일
    //@NotNull(message = "체크아웃 첨부파일은 필수입니다.")
    private Long outFileId;
}
