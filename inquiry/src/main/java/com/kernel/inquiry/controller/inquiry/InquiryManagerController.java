package com.kernel.inquiry.controller.inquiry;

import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.inquiry.service.dto.request.InquiryCreateReqDTO;
import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;
import com.kernel.inquiry.service.dto.request.InquiryUpdateReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquirySummaryRspDTO;
import com.kernel.inquiry.service.inquiry.InquiryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "문의사항 관리 API", description = "매니저 문의사항 조회, 생성, 수정, 삭제 API")
@RestController
@RequestMapping("/api/managers/inquiry")
@RequiredArgsConstructor
public class InquiryManagerController {

    private final InquiryService inquiryService;

    /**
     * 문의사항 목록 조회
     *
     * @param request  검색 조건을 포함하는 요청 DTO
     * @param pageable 페이징 정보
     * @return ResponseEntity<ApiResponse<Page<InquirySummaryRspDTO>>>
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<InquirySummaryRspDTO>>> searchInquiries(
            @AuthenticationPrincipal CustomUserDetails author,
            @RequestBody @Valid InquirySearchReqDTO request,
            Pageable pageable
    ) {

        Page<InquirySummaryRspDTO> inquiries = inquiryService.searchInquiries(request, author.getUserId(), pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 목록 조회 성공", inquiries));
    }

    /**
     * 문의사항 상세 조회
     *
     * @param inquiryId 문의사항 ID
     * @return ResponseEntity<ApiResponse<InquiryDetailRspDTO>>
     */
    @GetMapping("{inquiry-id}")
    public ResponseEntity<ApiResponse<InquiryDetailRspDTO>> getInquiryDetails(
            @PathVariable("inquiry-id") Long inquiryId
    ) {
        InquiryDetailRspDTO inquiryDetails = inquiryService.getInquiryDetails(inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 상세 조회 성공", inquiryDetails));
    }

    /**
     * 문의사항 생성
     *
     * @param request 요청 DTO
     * @return ResponseEntity<ApiResponse<Void>>
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createInquiry(
            @AuthenticationPrincipal CustomUserDetails author,
            @RequestBody @Valid InquiryCreateReqDTO request
    ) {

        inquiryService.createInquiry(request, author.getUserId(), author.getUser().getRole());

        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 등록 완료", null));
    }

    /**
     * 문의사항 수정
     *
     * @param request 요청 DTO
     * @return ResponseEntity<ApiResponse<Void>>
     */
    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> updateInquiry(
            @AuthenticationPrincipal CustomUserDetails author,
            @RequestBody @Valid InquiryUpdateReqDTO request
    ) {

        inquiryService.updateInquiry(request, author.getUserId());

        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 수정 완료", null));
    }

    /**
     * 문의사항 삭제
     *
     * @param inquiryId 삭제할 문의사항 ID
     * @return ResponseEntity<ApiResponse<Void>>
     */
    @DeleteMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(
            @AuthenticationPrincipal CustomUserDetails author,
            @PathVariable("inquiry_id") Long inquiryId
    ) {
        inquiryService.deleteInquiry(inquiryId, author.getUserId());

        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 삭제 완료", null));
    }

}
