package com.kernel.common.admin.controller;

import com.kernel.common.admin.dto.mapper.NoticeMapper;
import com.kernel.common.admin.dto.request.NoticeReqDto;
import com.kernel.common.admin.dto.response.NoticeResDto;
import com.kernel.common.admin.service.NoticeService;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.entity.Notice;
import com.kernel.common.global.enums.NoticeType;
import com.kernel.common.repository.NoticeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;

    // 공지사항 / 이벤트 등록
    @PostMapping("")
    public ResponseEntity<ApiResponse<NoticeResDto>> createNotice(
            @RequestBody @Valid NoticeReqDto requestDto
    ) {
        Notice notice = noticeService.createNotice(requestDto, 1L);
        NoticeResDto dto = NoticeMapper.toDto(notice);

        return ResponseEntity.ok(new ApiResponse<>(true, "게시판 등록 완료", dto));
    }

    // 공지사항 / 이벤트 목록 조회
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<NoticeResDto>>> getNoticeList(@RequestParam("type") NoticeType type) {
        List<NoticeResDto> dtoList = noticeService.getNoticeList(type);

        return ResponseEntity.ok(new ApiResponse<>(true, "게시판 목록 조회 완료", dtoList));
    }

    // 공지사항 / 이벤트 상세 조회
    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResDto>> getNoticeDetail(@PathVariable Long noticeId) {
        return noticeRepository.findById(noticeId)
                .map(notice -> ResponseEntity.ok(new ApiResponse<>(true, "게시글 상세 목록 조회 성공", NoticeMapper.toDto(notice))))
                .orElse(ResponseEntity.ok(new ApiResponse<>(false, "게시글이 없습니다.", null)));
    }

    // 공지사항 / 이벤트 수정
    @PatchMapping("/{noticeId}")
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

        NoticeResDto dto = NoticeMapper.toDto(notice);

        return ResponseEntity.ok(new ApiResponse<>(true, "게시글 수정 완료", dto));
    }

    // 공지사항 / 이벤트 삭제
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<Void>> deleteNotice(@PathVariable Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        notice.Deleted(true);
        return ResponseEntity.ok(new ApiResponse<>(true, "게시글 삭제 완료", null));
    }
}