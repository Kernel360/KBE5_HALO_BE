package com.kernel.common.customer.controller;


import com.kernel.common.customer.dto.request.CustomerInquiryCreateReqDTO;
import com.kernel.common.customer.dto.request.CustomerInquiryUpdateReqDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryDetailRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import com.kernel.common.customer.dto.response.InquiryCategoryRspDTO;
import com.kernel.common.customer.service.CustomerInquiryService;
import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/customers/inquiries")
@RequiredArgsConstructor
public class CustomerInquiryController {

    private final CustomerInquiryService inquiryService;

    /**
     * 수요자 문의사항 카테고리 조회
     * @return 문의사항 카테고리 목록
     */
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<InquiryCategoryRspDTO>>> getCustomerInquiryCategory(){

        List<InquiryCategoryRspDTO> rspDTOList = inquiryService.getCustomerInquiryCategory();

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 카테고리 조회 성공",rspDTOList));
    }


    /**
     * 수요자 문의사항 조회
     * @param startDate  검색 시작날짜
     * @param pageable 페이징 정보
     * @param customer 수요자ID
     * @return 검색된 문의사항 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerInquiryRspDTO>>> searchCustomerInquiries(
            @RequestParam(required = false) LocalDateTime startDate,
            @AuthenticationPrincipal AuthenticatedUser customer,
            @PageableDefault(page = 0, size = 10) Pageable pageable
   ) {
        Page<CustomerInquiryRspDTO> rspDTOPage =
                inquiryService.searchCustomerInquiries(customer.getUserId(), startDate, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 목록 조회 성공", rspDTOPage));
    }

    /**
     * 수요자 문의사항 상세 조회
     * @param inquiryId 문의사항 ID
     * @param customer 수요자ID
     * @return 상세 문의사항 정보
     */
    @GetMapping("{inquiry_id}")
    public ResponseEntity<ApiResponse<CustomerInquiryDetailRspDTO>> getCustomerInquiryDetails(
            @PathVariable("inquiry_id") Long inquiryId,
            @AuthenticationPrincipal AuthenticatedUser customer
    ) {
        CustomerInquiryDetailRspDTO rspDTO =
                inquiryService.getCustomerInquiryDetails(customer.getUserId(), inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 상세 조회 성공", rspDTO));
    }

    /**
     * 수요자 문의사항 등록
     * @param inquiryRequestDTO 문의사항 등록 요청 데이터
     * @param customer 수요자ID
     * @return 등록된 문의사항 응답
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerInquiryDetailRspDTO>> createCustomerInquiry(
            @Valid @RequestBody CustomerInquiryCreateReqDTO inquiryRequestDTO,
            @AuthenticationPrincipal AuthenticatedUser customer
    ) {
        CustomerInquiryDetailRspDTO rspDTO =
                inquiryService.createCustomerInquiry(customer.getUserId(), inquiryRequestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 등록 성공", rspDTO));
    }

    /**
     * 수요자 문의사항 수정
     * @param inquiryRequestDTO 문의사항 수정 요청 데이터
     * @param customer 문의사항 수정 요청 데이터
     * @param inquiry_id 문의사항ID
     * @return 수정된 문의사항 응답
     */
    @PatchMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<CustomerInquiryDetailRspDTO>> updateCustomerInquiry(
            @Valid @RequestBody CustomerInquiryUpdateReqDTO inquiryRequestDTO,
            @AuthenticationPrincipal AuthenticatedUser customer,
            @PathVariable("inquiry_id") Long inquiry_id
    ){
        CustomerInquiryDetailRspDTO rspDTO =
                inquiryService.updateCustomerInquiry(customer.getUserId(), inquiry_id, inquiryRequestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 수정 성공", rspDTO));
    }

    /**
     * 수요자 문의사항 삭제
     * @param inquiryId 문의사항 ID
     * @return 성공 응답
     */
    @DeleteMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomerInquiry(
            @PathVariable("inquiry_id") Long inquiryId,
            @AuthenticationPrincipal AuthenticatedUser customer
    ) {
        inquiryService.deleteCustomerInquiry(customer.getUserId(), inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 삭제 성공", null));
    }

}

