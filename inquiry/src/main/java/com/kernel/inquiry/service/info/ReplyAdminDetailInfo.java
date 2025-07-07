package com.kernel.inquiry.service.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyAdminDetailInfo {

    private Long replyId;
    private String userName;
    private String content;
    private Long fileId;
    private LocalDateTime createdAt;
}
