package com.kernel.common.manager.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerInquirySummaryRspDTO {

    // 매니저 게시글ID
    private Long inquiryId;

    // 제목
    private String title;

    // 작성일시(= 생성일시)
    private LocalDateTime createdAt;

    // 답변 여부
    private Boolean isReplied;
}
