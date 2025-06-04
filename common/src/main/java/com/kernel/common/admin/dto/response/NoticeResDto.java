package com.kernel.common.admin.dto.response;

import com.kernel.common.global.enums.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NoticeResDto {

    private Long noticeId;
    private NoticeType noticeType;
    private String title;
    private String content;
    private Long fileId;
    private Boolean deleted;
    private Long views;
}
