package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.request.NoticeReqDto;
import com.kernel.common.admin.dto.response.NoticeResDto;
import com.kernel.common.global.entity.Notice;
import com.kernel.common.global.enums.NoticeType;

import java.util.List;

public interface NoticeService {

    Notice createNotice(NoticeReqDto dto, Long adminId);

    List<NoticeResDto> getNoticeList(NoticeType type);
}