package com.kernel.admin.controller;

import com.kernel.admin.dto.request.NoticeReqDto;
import com.kernel.admin.dto.response.NoticeResDto;
import com.kernel.admin.notice.Notice;
import com.kernel.admin.notice.NoticeRepository;
import com.kernel.admin.notice.NoticeService;
import com.kernel.admin.notice.NoticeType;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class NoticeController {

    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;

    public NoticeController(NoticeRepository noticeRepository, NoticeService noticeService) {
        this.noticeRepository = noticeRepository;
        this.noticeService = noticeService;
    }

    // 공지사항 / 이벤트 등록
    @PostMapping("/notices")
    public ResponseEntity<NoticeResDto> createNotice(
            @RequestBody @Valid NoticeReqDto requestDto
            // 스프링 시큐리티에서 로그인 정보 받아오 것
            // @AuthenticationPrincipal Admin admin
    ) {
        // 임의로 어드민 아이디 1L 설정, 시큐리티 설정 후 admin.getId()로 수정하기
        Notice notice = noticeService.createNotice(requestDto, 1L);
        return ResponseEntity.ok(NoticeResDto.from(notice));
    }

    // 공지사항 / 이벤트 목록 조회
    @GetMapping("/notices")
    public List<Notice> getNoticeList(@RequestParam("type") NoticeType type) {
        return noticeRepository.findByNoticeType(type);
    }

    // 공지사항 / 이벤트 상세 조회
    @GetMapping("/notices/{noticeId}")
    public ResponseEntity<NoticeResDto> getNoticeDetail(@PathVariable Long noticeId) {
        return noticeRepository.findById(noticeId)
                .map(notice -> ResponseEntity.ok(NoticeResDto.from(notice)))
                .orElse(ResponseEntity.notFound().build());
    }

    // 공지사항 / 이벤트 수정
    @PatchMapping("/notices/{noticeId}")
    public ResponseEntity<NoticeResDto> updateNotice(
            @PathVariable Long noticeId,
            @RequestBody NoticeReqDto requestDto
            // @AuthenticationPrincipal Admin admin
    ) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        // 수정하는 관리자 ID를 1L로 고정, 추후 로그인 정보에서 받아와서 사용
        notice.update(
                requestDto.getTitle(),
                requestDto.getContent(),
                null,
                1L
        );

        return ResponseEntity.ok(NoticeResDto.builder()
                .noticeId(notice.getNoticeId())
                .noticeType(notice.getNoticeType())
                .title(notice.getTitle())
                .content(notice.getContent())
                .fileId(notice.getFileId())
                .isDeleted(notice.getIsDeleted())
                .createdAt(notice.getCreatedAt())
                .createdBy(notice.getCreatedBy())
                .updatedAt(notice.getUpdatedAt())
                .updatedBy(notice.getUpdatedBy())
                .views(notice.getViews())
                .build());
    }

    // 공지사항 / 이벤트 삭제
    @DeleteMapping("/notices/{noticeId}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        notice.setIsDeleted(true);
        return ResponseEntity.noContent().build();
    }

}
