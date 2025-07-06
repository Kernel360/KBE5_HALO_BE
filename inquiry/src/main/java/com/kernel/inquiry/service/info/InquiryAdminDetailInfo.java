package com.kernel.inquiry.service.info;

import com.kernel.inquiry.common.enums.AuthorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryAdminDetailInfo {

    private Long inquiryId;
    private String categoryName;
    private String title;
    private String content;
    private Long authorId;
    private String authorName;
    private AuthorType authorType;
    private String phone;
    private String email;
    private Long fileId;
    private LocalDateTime createdAt;
    private ReplyAdminDetailInfo replyDetailInfo;

    public void initReplyInfo(ReplyAdminDetailInfo replyDetailInfo) {
        this.replyDetailInfo = replyDetailInfo;
    }

}
