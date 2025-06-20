package com.kernel.inquiry.controller.inquiry;

import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.inquiry.service.dto.request.InquirySearchReqDTO;
import com.kernel.inquiry.service.dto.response.InquiryDetailRspDTO;
import com.kernel.inquiry.service.dto.response.InquirySummaryRspDTO;
import com.kernel.inquiry.service.inquiry.InquiryQueryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inquiry/query")
@RequiredArgsConstructor
public class InquiryQueryController {

    private final InquiryQueryService inquiryQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<InquirySummaryRspDTO>>> searchInquiries(
            //@AuthenticationPrincipal AuthenticatedUser authorId,
            @RequestBody @Valid InquirySearchReqDTO request,
            Pageable pageable
    ) {
        // 현재는 authorId를 1L로 하드코딩되어 있으나, 실제 서비스에서는 사용자의 ID를 받아와야 합니다.
        //Page<InquirySummaryRspDTO> inquiries = inquiryQueryService.searchInquiries(request, authorId, pageable);
        Page<InquirySummaryRspDTO> inquiries = inquiryQueryService.searchInquiries(request, 1L, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 목록 조회 성공", inquiries));
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<InquiryDetailRspDTO>> getInquiryDetails(
            @RequestBody Long inquiryId
            ) {
        InquiryDetailRspDTO inquiryDetails = inquiryQueryService.getInquiryDetails(inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 상세 조회 성공", inquiryDetails));
    }
}
