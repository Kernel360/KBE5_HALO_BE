package com.kernel.common.customer.controller;


import com.kernel.common.customer.dto.response.CustomerInquiryDetailRspDTO;
import com.kernel.common.customer.dto.response.CustomerInquiryRspDTO;
import com.kernel.common.customer.service.CustomerInquiryService;
import com.kernel.common.global.entity.ApiResponse;
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

    /*
    * 수요자 문의사항 조회
    * @Param 수요자ID
    * @Param 검색 키워드
    * @Param Pageable 페이징
    * @Return 검색 키워드에 맞는 문의사항 목록
    */
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<CustomerInquiryRspDTO>>> searchCustomerInquiries(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10) Pageable pageable
            // TODO 유저아이디
    ) {
        Page<CustomerInquiryRspDTO>  rspDTOPage = inquiryService.searchCustomerInquiries(1L, keyword, pageable);

        String message = rspDTOPage.isEmpty() ? "검색 결과가 없습니다." : "수요자 문의사항 목록 조회 성공";

        return ResponseEntity.ok(new ApiResponse<>(true, message, rspDTOPage));
    }

    /*
     * 수요자 문의사항 상세 조회
     * @Param 문의사항ID
     * @Param 수요자ID
     * @Param Pageable 페이징
     * @Return 검색 키워드에 맞는 문의사항 목록
     */
    @GetMapping("{inquiry_id}")
    public ResponseEntity<ApiResponse<CustomerInquiryDetailRspDTO>> getCustomerInquiryDetails(
            @PathVariable("inquiry_id") Long inquiry_id
            // TODO 유저아이디
    ) {
        CustomerInquiryDetailRspDTO rspDTO = inquiryService.getCustomerInquiryDetails(1L, inquiry_id);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 문의사항 상세 조회 성공", rspDTO));
    }
/*
    //create
    @PostMapping("")
    public ResponseEntity<ApiResponse<InquiryResponseDTO>> createInquiry(@RequestBody InquiryRequestDTO inquiryRequestDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "success", inquiryService.createInquiry(inquiryRequestDTO)));
    }

    //update
    @PatchMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<InquiryResponseDTO>> updateInquiry(@RequestBody InquiryRequestDTO inquiryRequestDTO, @PathVariable Long inquiry_id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "success", inquiryService.updateInquiry(inquiryRequestDTO, inquiry_id)));
    }

    //delete
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(@PathVariable Long inquiryId) {
        //noContent() 메서드는 HTTP 204 Status Code와 함께 Response를 생성할 때 사용된다.
        // 주로 삭제 요청이나 업데이트 후 body가 필요 없는 경우에 사용된다.
        inquiryService.deleteInquiry(inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }*/
}

