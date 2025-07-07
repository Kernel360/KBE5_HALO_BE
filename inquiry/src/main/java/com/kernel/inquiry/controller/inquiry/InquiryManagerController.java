package com.kernel.inquiry.controller.inquiry;

import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.global.service.dto.response.EnumValueDTO;
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

import java.util.List;

@Tag(name = "매니저 문의사항 관리 API", description = "매니저 문의사항 조회, 생성, 수정, 삭제 API")
@RestController
@RequestMapping("/api/managers/inquiries")
@RequiredArgsConstructor
public class InquiryManagerController {

    private final InquiryService inquiryService;

    /**
     * 매니저 문의사항 카테고리 조회
     * @return 문의사항 카테고리 목록
     */
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<EnumValueDTO>>> getManagerInquiryCategory() {

        List<EnumValueDTO> result = inquiryService.getManagerInquiryCategory();

        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 문의사항 카테고리 조회 성공", result));
    }

    /**
     * 매니저 문의사항 조회
     * @param searchReqDTO 검색 조건
     * @param pageable     페이징 정보
     * @param user         매니저ID
     * @return 검색된 문의사항 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<InquirySummaryRspDTO>>> searchCustomerInquiries(
            @ModelAttribute @Valid InquirySearchReqDTO searchReqDTO,
            @AuthenticationPrincipal CustomUserDetails user,
            Pageable pageable
    ) {
        Page<InquirySummaryRspDTO> inquiries =
                inquiryService.searchInquiries(searchReqDTO, user.getUserId(), pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 문의사항 목록 조회 성공", inquiries));
    }

    /**
     * 수요자 문의사항 상세 조회
     * @param inquiryId 문의사항 ID
     * @param user      매니저ID
     * @return 상세 문의사항 정보
     */
    @GetMapping("/{inquiry-id}")
    public ResponseEntity<ApiResponse<InquiryDetailRspDTO>> getInquiryDetails(
            @PathVariable("inquiry-id") Long inquiryId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        InquiryDetailRspDTO inquiryDetails = inquiryService.getInquiryDetails(inquiryId, user.getUserId());
        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 상세 조회 성공", inquiryDetails));
    }

    /**
     * 문의사항 등록
     * @param createReqDTO 요청 DTO
     * @param user         매니저ID
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createInquiry(
            @RequestBody @Valid InquiryCreateReqDTO createReqDTO,
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        Long savedInquiry = inquiryService.createInquiry(createReqDTO, user.getUserId(), user.getRole());

        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 등록 성공", savedInquiry));
    }

    /**
     * 문의사항 수정
     * @param inquiryId 문의사항ID
     * @param updateReqDTO 요청 DTO
     * @param user         수요자ID
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @PatchMapping("/{inquiry-id}")
    public ResponseEntity<ApiResponse<Void>> updateInquiry(
            @PathVariable("inquiry-id") Long inquiryId,
            @RequestBody @Valid InquiryUpdateReqDTO updateReqDTO,
            @AuthenticationPrincipal CustomUserDetails user
    ) {

        inquiryService.updateInquiry(inquiryId, updateReqDTO, user.getUserId(), user.getRole());

        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 수정 완료", null));
    }

    /**
     * 문의사항 삭제
     * @param inquiryId 삭제할 문의사항 ID
     * @param user      수요자ID
     * @return ResponseEntity<ApiResponse < Void>>
     */
    @DeleteMapping("/{inquiry-id}")
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(
            @PathVariable("inquiry-id") Long inquiryId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        inquiryService.deleteInquiry(inquiryId, user.getUserId());

        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 삭제 완료", null));
    }
}
