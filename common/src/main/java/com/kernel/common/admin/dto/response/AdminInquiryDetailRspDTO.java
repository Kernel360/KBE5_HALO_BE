package com.kernel.common.admin.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminInquiryDetailRspDTO extends AdminInquirySummaryRspDTO {

    // 문의사항 내용
    private String content;

    // 문의사항 첨부파일 ID
    private Long fileId;


    // 답변 내용
    private String reply;

    // 답변 첨부파일 ID
    private Long replyFileId;

    // 답변 작성 시간
    private LocalDateTime replyCreatedAt;

    // 답변 작성자 ID
    private Long replyAuthorId;

}