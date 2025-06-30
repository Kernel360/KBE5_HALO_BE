package com.kernel.inquiry.controller.inquiry;

import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;
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

@Tag(name = "문의사항 관리 API", description = "관리자 문의사항 조회 API")
@RestController
@RequestMapping("/api/admin/inquiry")
@RequiredArgsConstructor
public class InquiryAdminController {

    private final InquiryService inquiryService;

    // 문의사항 목록 조회
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<InquirySummaryRspDTO>>> searchInquiries(
            @AuthenticationPrincipal CustomUserDetails author,
            @RequestBody @Valid InquirySearchReqDTO request,
            Pageable pageable
    ) {
        Page<InquirySummaryRspDTO> inquiries = inquiryService.searchInquiries(request, author.getUserId(), pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 목록 조회 성공", inquiries));
    }

    // 문의사항 상세 조회
    @GetMapping("{inquiry-id}")
    public ResponseEntity<ApiResponse<InquiryDetailRspDTO>> getInquiryDetails(
            @PathVariable("inquiry-id") Long inquiryId
    ) {
        InquiryDetailRspDTO inquiryDetails = inquiryService.getInquiryDetails(inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 상세 조회 성공", inquiryDetails));
    }
}
