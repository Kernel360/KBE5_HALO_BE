package com.kernel.common.customer.controller;


import com.kernel.common.customer.dto.request.CustomerInquiryCreateReqDTO;
import com.kernel.common.customer.dto.request.CustomerInquiryUpdateReqDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryDetailRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import com.kernel.common.customer.service.CustomerInquiryService;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/inquiries")
@RequiredArgsConstructor
public class CustomerInquiryController {

    private final CustomerInquiryService inquiryService;

    /**
     * 수요자 문의사항 조회
     *
     * @param keyword  검색 키워드 (선택)
     * @param pageable 페이징 정보
     * @return 검색된 문의사항 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerInquiryRspDTO>>> searchCustomerInquiries(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 3) Pageable pageable // TODO 페이지 전역 설정 확인
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
    ) {
        Page<CustomerInquiryRspDTO> rspDTOPage =
                inquiryService.searchCustomerInquiries(1L, keyword, pageable);

        String message = rspDTOPage.isEmpty() ? "검색 결과가 없습니다." : "수요자 문의사항 목록 조회 성공";
        return ResponseEntity.ok(new ApiResponse<>(true, message, rspDTOPage));
    }

    /**
     * 수요자 문의사항 상세 조회
     *
     * @param inquiryId 문의사항 ID
     * @return 상세 문의사항 정보
     */
    @GetMapping("{inquiry_id}")
    public ResponseEntity<ApiResponse<CustomerInquiryDetailRspDTO>> getCustomerInquiryDetails(
            @PathVariable("inquiry_id") Long inquiryId
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
    ) {
        CustomerInquiryDetailRspDTO rspDTO =
                inquiryService.getCustomerInquiryDetails(1L, inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 상세 조회 성공", rspDTO));
    }

    /**
     * 수요자 문의사항 등록
     *
     * @param inquiryRequestDTO 문의사항 등록 요청 데이터
     * @return 등록된 문의사항 응답
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerInquiryDetailRspDTO>> createCustomerInquiry(
            @Valid @RequestBody CustomerInquiryCreateReqDTO inquiryRequestDTO
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
    ) {
        CustomerInquiryDetailRspDTO rspDTO =
                inquiryService.createCustomerInquiry(1L, inquiryRequestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 등록 성공", rspDTO));
    }

    /**
     * 수요자 문의사항 수정
     *
     * @param inquiryRequestDTO 문의사항 수정 요청 데이터
     * @return 수정된 문의사항 응답
     */
    @PatchMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<CustomerInquiryDetailRspDTO>> updateCustomerInquiry(
            @Valid @RequestBody CustomerInquiryUpdateReqDTO inquiryRequestDTO
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
    ) {
        CustomerInquiryDetailRspDTO rspDTO =
                inquiryService.updateCustomerInquiry(1L, inquiryRequestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 수정 성공", rspDTO));
    }

    /**
     * 수요자 문의사항 삭제
     *
     * @param inquiryId 문의사항 ID
     * @return 성공 응답
     */
    @DeleteMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomerInquiry(
            @PathVariable("inquiry_id") Long inquiryId
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
    ) {
        inquiryService.deleteCustomerInquiry(1L, inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 삭제 성공", null));
    }

}

