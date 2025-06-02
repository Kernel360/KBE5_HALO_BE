package com.kernel.common.manager.dto.response;

import com.kernel.common.manager.entity.ManagerReply;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerInquiryRspDTO {

    // 매니저 게시글ID
    private Long inquiryId;

    // 작성자ID(= 매니저ID)
    private Long authorId;

    // 제목
    private String title;

    // 내용
    private String content;

    // 첨부파일
    // TODO: 첨부파일 추후 작업 예정
//    private Long fileId;

    // 작성일시(= 생성일시)
    private LocalDateTime createdAt;

    // 답변
    private ManagerReply managerReply;
}
