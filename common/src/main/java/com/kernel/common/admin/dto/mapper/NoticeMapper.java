package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.response.NoticeResDto;
import com.kernel.common.global.entity.Notice;

public class NoticeMapper {
    public static NoticeResDto toDto(Notice notice) {
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