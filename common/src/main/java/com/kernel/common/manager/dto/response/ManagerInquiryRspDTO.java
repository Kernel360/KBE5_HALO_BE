package com.kernel.common.manager.dto.response;

import com.kernel.common.manager.entity.ManagerReply;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.convert.Jsr310Converters.LocalDateTimeToInstantConverter;

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
    private Long fileId;

    // 작성일시(= 생성일시)
    private LocalDateTime createdAt;

    // 답변
    private ManagerReply managerReply;

    // 답변ID
    private Long answerId;

    // 답변내용
    private String replyContent;

    // 답변첨부파일ID
    private Long replyFileId;

    // 답변일시
    private LocalDateTime replyCreatedAt;
}
