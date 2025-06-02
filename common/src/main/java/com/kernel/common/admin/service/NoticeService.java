package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.request.NoticeReqDto;
import com.kernel.common.admin.dto.response.NoticeResDto;
import com.kernel.common.admin.entity.Notice;
import com.kernel.common.admin.entity.NoticeType;
import com.kernel.common.admin.repository.NoticeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                .is_Deleted(false)
                .views(0L)
                .build();

        return noticeRepository.save(notice);
    }

    // 공지사항 / 이벤트 목록 조회 메서드 추가
    public List<NoticeResDto> getNoticeList(NoticeType type) {
        return noticeRepository.findByNoticeType(type).stream()
                .map(NoticeResDto::from)
                .collect(Collectors.toList());
    }
}