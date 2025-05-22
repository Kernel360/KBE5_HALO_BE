package com.kernel.customer.controller;

import com.kernel.common.entity.ApiResponse;
import com.kernel.customer.dto.request.InquiryRequestDTO;
import com.kernel.customer.dto.response.InquiryResponseDTO;
import com.kernel.customer.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<InquiryResponseDTO>>> getInquires(
            @RequestParam(required = false) String keyword
    ) {
        List<InquiryResponseDTO> responseList = inquiryService.getInquiries(keyword);

        return ResponseEntity.ok(new ApiResponse<>(true, "성공", responseList));
    }

    //create
    @PostMapping("")
    public ResponseEntity<ApiResponse<InquiryResponseDTO>> createInquiry(@RequestBody InquiryRequestDTO inquiryRequestDTO) {
        return ResponseEntity.ok(new ApiResponse<>(true, "성공", inquiryService.createInquiry(inquiryRequestDTO)));
    }

    //update
    @PatchMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<InquiryResponseDTO>> updateInquiry(@RequestBody InquiryRequestDTO inquiryRequestDTO, @PathVariable Long inquiry_id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "성공", inquiryService.updateInquiry(inquiryRequestDTO, inquiry_id)));
    }

    //delete
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(@PathVariable Long inquiryId) {
        //noContent() 메서드는 HTTP 204 Status Code와 함께 Response를 생성할 때 사용된다.
        // 주로 삭제 요청이나 업데이트 후 body가 필요 없는 경우에 사용된다.
        inquiryService.deleteInquiry(inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "성공", null));
    }
}

