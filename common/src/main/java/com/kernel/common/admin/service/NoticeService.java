package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.request.NoticeReqDto;
import com.kernel.common.admin.entity.Notice;
import com.kernel.common.admin.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 공지사항 / 이벤트 등록 메서드
    @Transactional
    public Notice createNotice(NoticeReqDto dto, Long adminId) {
        Notice notice = Notice.builder()
                .noticeType(dto.getNoticeType())
                .title(dto.getTitle())
                .content(dto.getContent())
                // .fileId(null)
                // TODO:파일 첨부 구현 후 주석 해제
                .Deleted(false)
                .views(0L)
                .build();

        return noticeRepository.save(notice);
    }
}
