package com.kernel360.Halo.web.customer.controller;

import com.kernel360.Halo.domain.customer.service.InquiryService;
import com.kernel360.Halo.web.common.ApiResponse;
import com.kernel360.Halo.web.customer.dto.request.InquiryRequestDTO;
import com.kernel360.Halo.web.customer.dto.response.InquiryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/inquiries")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    //read return ResponseDTO
    @GetMapping("")
    public ResponseEntity<List<InquiryResponseDTO>> getInquires(
            @RequestParam String keyword
    ) {
        List<InquiryResponseDTO> responseList = inquiryService.getInquiries(keyword);
        // keyword == all이면 전체 조회
        // keyword != all이면 검색
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    //read return ApiResponse
    @GetMapping("/2")
    public ResponseEntity<ApiResponse<List<InquiryResponseDTO>>> getInquires2(
            @RequestParam String keyword
    ) {
        List<InquiryResponseDTO> responseList = inquiryService.getInquiries(keyword);
        // keyword == all이면 전체 조회
        // keyword != all이면 검색
        return ResponseEntity.ok(new ApiResponse<>(true, "성공", responseList));
    }

    //create
    @PostMapping("")
    public ResponseEntity<InquiryResponseDTO> createInquiry(@RequestBody InquiryRequestDTO inquiryRequestDTO) {
        System.out.println("create");

        return ResponseEntity.status(HttpStatus.OK).body(inquiryService.createInquiry(inquiryRequestDTO));

    }

    //update
    @PatchMapping("/{inquiry_id}")
    public ResponseEntity<InquiryResponseDTO> updateInquiry(@RequestBody InquiryRequestDTO inquiryRequestDTO, @PathVariable Long inquiry_id) {
        return ResponseEntity.ok(inquiryService.updateInquiry(inquiryRequestDTO, inquiry_id));
    }

    //delete
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long inquiryId) {
        //noContent() 메서드는 HTTP 204 Status Code와 함께 Response를 생성할 때 사용된다.
        // 주로 삭제 요청이나 업데이트 후 body가 필요 없는 경우에 사용된다.
        inquiryService.deleteInquiry(inquiryId);
        return ResponseEntity.noContent().build();
    }
}

