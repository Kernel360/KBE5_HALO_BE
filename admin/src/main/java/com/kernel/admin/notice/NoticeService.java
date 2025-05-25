package com.kernel.admin.notice;

import com.kernel.admin.dto.request.NoticeReqDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    // 공지사항 / 이벤트 등록 메서드
    @Transactional
    public Notice createNotice(NoticeReqDto dto, Long adminId) {
        Notice notice = new Notice();
        notice.setNoticeType(dto.getNoticeType());
        notice.setTitle(dto.getTitle());
        notice.setContent(dto.getContent());
        // TODO:파일 첨부 구현 후 값 세팅 예정
        // notice.setFileId(null);
        notice.setIsDeleted(false);
        notice.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        notice.setCreatedBy(adminId);
        notice.setUpdatedAt(null);
        notice.setUpdatedBy(null);
        notice.setViews(0L);

        return noticeRepository.save(notice);
    }
}
