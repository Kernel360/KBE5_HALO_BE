package com.kernel.common.admin.controller;

import com.kernel.common.admin.dto.request.NoticeReqDto;
import com.kernel.common.admin.dto.response.NoticeResDto;
import com.kernel.common.admin.entity.Notice;
import com.kernel.common.admin.entity.NoticeType;
import com.kernel.common.admin.repository.NoticeRepository;
import com.kernel.common.admin.service.NoticeService;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<ApiResponse<NoticeResDto>> createNotice(
            @RequestBody @Valid NoticeReqDto requestDto
    ) {
        Notice notice = noticeService.createNotice(requestDto, 1L);
        NoticeResDto dto = NoticeResDto.from(notice);

        return ResponseEntity.ok(new ApiResponse<>(true, "success", dto));
    }

    // 공지사항 / 이벤트 목록 조회
    @GetMapping("/notices")
    public ResponseEntity<ApiResponse<List<NoticeResDto>>> getNoticeList(@RequestParam("type") NoticeType type) {
        List<Notice> noticeList = noticeRepository.findByNoticeType(type);
        List<NoticeResDto> dtoList = noticeList.stream()
                .map(NoticeResDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse<>(true, "success", dtoList));
    }

    // 공지사항 / 이벤트 상세 조회
    @GetMapping("/notices/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResDto>> getNoticeDetail(@PathVariable Long noticeId) {

        return noticeRepository.findById(noticeId)
                .map(notice -> ResponseEntity.ok(new ApiResponse<>(true, "success", NoticeResDto.from(notice))))
                .orElse(ResponseEntity.ok(new ApiResponse<>(false, "게시글이 없습니다.", null)));
    }

    // 공지사항 / 이벤트 수정
    @PatchMapping("/notices/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResDto>> updateNotice(
            @PathVariable Long noticeId,
            @RequestBody NoticeReqDto requestDto
    ) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        notice.update(
                requestDto.getTitle(),
                requestDto.getContent(),
                null,
                1L
        );

        NoticeResDto dto = NoticeResDto.builder()
                .noticeId(notice.getNoticeId())
                .noticeType(notice.getNoticeType())
                .title(notice.getTitle())
                .content(notice.getContent())
                .fileId(notice.getFileId())
                .deleted(notice.getDeleted())
                .views(notice.getViews())
                .build();

        return ResponseEntity.ok(new ApiResponse<>(true, "success", dto));
    }

    // 공지사항 / 이벤트 삭제
    @DeleteMapping("/notices/{noticeId}")
    public ResponseEntity<ApiResponse<Void>> deleteNotice(@PathVariable Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        notice.setDeleted(true);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }
}