package com.kernel.common.manager.controller;

import com.kernel.common.entity.ApiResponse;
import com.kernel.common.enums.ReplyStatus;
import com.kernel.common.manager.dto.reponse.ManagerInquiryRspDTO;
import com.kernel.common.manager.dto.reponse.ManagerInquirySummaryRspDTO;
import com.kernel.common.manager.dto.request.ManagerInquiryCreateReqDTO;
import com.kernel.common.manager.dto.request.ManagerInquiryUpdateReqDTO;
import com.kernel.common.manager.service.ManagerInquiryService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers/inquiries")
public class ManagerInquiryController {

    private final ManagerInquiryService managerInquiryService;

    /**
     * 매니저 상담 게시글 목록 조회 API (검색 조건 및 페이징 처리)
     * @param fromDate 작성일시 시작일
     * @param toDate 작성일시 종료일
     * @param replyStatus 답변상태
     * @param titleKeyword 제목키워드
     * @param contentKeyword 내용키워드
     * @param pageable 페이지 정보 (페이지 번호, 크기, 정렬 기준 등)
     * @return 검색 조건에 따른 게시글 목록을 응답 (페이징 포함)
     */
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<ManagerInquirySummaryRspDTO>>> searchManagerInquiries(
        @RequestParam(value = "fromCreatedAt", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
        @RequestParam(value = "toCreatedAt", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
        @RequestParam(value = "replyStatus", required = false) ReplyStatus replyStatus,
        @RequestParam(value = "titleKeyword", required = false) String titleKeyword,
        @RequestParam(value = "contentKeyword", required = false) String contentKeyword,
        Pageable pageable
    ) {
        // LocalDate → LocalDateTime 보정
        LocalDateTime fromCreatedAt = fromDate != null ? fromDate.atStartOfDay() : null;
        LocalDateTime toCreatedAt = toDate != null ? toDate.atTime(23, 59, 59) : null;

        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        Page<ManagerInquirySummaryRspDTO> summaryRspDTOPage
            = managerInquiryService.searchManagerinquiriesWithPaging(1L, fromCreatedAt, toCreatedAt, replyStatus, titleKeyword, contentKeyword, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 상담 게시글 목록 조회 성공", summaryRspDTOPage));
    }

    /**
     * 매니저 상담 게시글 상세조회 API
     * @param inquiryId 게시글 ID
     * @return 게시글 상세 정보를 담은 응답
     */
    @GetMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<ManagerInquiryRspDTO>> getManagerInquiry(
        @PathVariable("inquiry_id") Long inquiryId
    ) {
        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        ManagerInquiryRspDTO rspDTO = managerInquiryService.getManagerInquiry(1L, inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 상담 게시글 상세 조회 성공", rspDTO));
    }

    /**
     * 매니저 상담 게시글 등록 API
     * @param requestDTO 게시글 등록 요청 데이터
     * @return 작성된 게시글 정보를 담은 응답
     */
    @PostMapping("")
    public ResponseEntity<ApiResponse<ManagerInquirySummaryRspDTO>> createManagerInquiry(
            @Valid @RequestBody ManagerInquiryCreateReqDTO requestDTO
    ) {
        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        ManagerInquirySummaryRspDTO summaryRspDTO = managerInquiryService.createManagerInquiry(1L, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 상담 게시글 등록 성공", summaryRspDTO));
    }

    /**
     * 매니저 상담 게시글 수정 API
     * @param inquiryId 게시글 ID
     * @param requestDTO 게시글 수정 및 삭제 요청 데이터
     * @return 수정된 게시글 정보를 담은 응답
     */
    @PatchMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<ManagerInquirySummaryRspDTO>> updateManagerInquiry(
            @PathVariable("inquiry_id") Long inquiryId,
            @Valid @RequestBody ManagerInquiryUpdateReqDTO requestDTO
    ) {
        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        ManagerInquirySummaryRspDTO summaryRspDTO = managerInquiryService.updateManagerInquiry(1L, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 상담 게시글 수정 성공", summaryRspDTO));
    }

    /**
     * 매니저 상담 게시글 삭제 API
     * @param inquiryId 게시글 ID
     * @return null
     */
    @DeleteMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<ManagerInquirySummaryRspDTO>> updateManagerInquiry(
        @PathVariable("inquiry_id") Long inquiryId
    ) {
        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        managerInquiryService.deleteManagerInquiry(1L, inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 상담 게시글 삭제 성공", null));
    }
}
