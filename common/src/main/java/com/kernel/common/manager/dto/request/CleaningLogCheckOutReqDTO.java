package com.kernel.common.manager.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CleaningLogCheckOutReqDTO {

    // 체크아웃 첨부파일
    @NotNull(message = "체크아웃 일시는 필수입니다.")
    private Long outFileId;
}
