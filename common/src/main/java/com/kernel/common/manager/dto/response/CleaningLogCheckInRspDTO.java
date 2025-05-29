package com.kernel.common.manager.dto.response;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CleaningLogCheckInRspDTO {

    // 체크ID
    @NotNull
    private Long checkId;

    // 예약ID
    @NotNull
    private Long reservationId;

    // 체크인 일시
    @NotNull
    private LocalDateTime inTime;

    // 체크인 첨부파일
    @NotNull
    private Long inFileId;
}
