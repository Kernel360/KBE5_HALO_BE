package com.kernel.common.admin.dto.response;

import com.kernel.common.admin.entity.Notice;
import com.kernel.common.admin.entity.NoticeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
    private Timestamp createdAt;
    private Long createdBy;
    private Timestamp updatedAt;
    private Long updatedBy;
    private Long views;

    public static NoticeResDto from(Notice notice) {
        return NoticeResDto.builder()
                .noticeId(notice.getNoticeId())
                .noticeType(notice.getNoticeType())
                .title(notice.getTitle())
                .content(notice.getContent())
                .fileId(notice.getFile_Id())
                .deleted(notice.getIs_Deleted())
                .views(notice.getViews())
                .build();
    }
}
