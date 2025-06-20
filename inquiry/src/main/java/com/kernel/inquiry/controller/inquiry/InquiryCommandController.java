package com.kernel.inquiry.controller.inquiry;

import com.kernel.global.domain.entity.ApiResponse;
import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.service.dto.request.InquiryCreateReqDTO;
import com.kernel.inquiry.service.dto.request.InquiryDeleteReqDTO;
import com.kernel.inquiry.service.dto.request.InquiryUpdateReqDTO;
import com.kernel.inquiry.service.inquiry.InquiryCommandService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inquiry/command")
@RequiredArgsConstructor
public class InquiryCommandController {

    private final InquiryCommandService inquiryCommandService;

    /**
     * 문의사항 생성
     *
     * @param request 요청 DTO
     * @return ResponseEntity<ApiResponse<Void>>
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createInquiry(
            //@AuthenticationPrincipal AuthenticatedUser author,
            @RequestBody @Valid InquiryCreateReqDTO request
    ) {
        // 현재는 인증된 사용자의 ID를 1L로 하드코딩, 실제로는 인증된 사용자 정보를 사용해야 함
        //inquiryCommandService.createInquiry(request, author.getId(), author.getAuthorType());
        inquiryCommandService.createInquiry(request, 1L, AuthorType.CUSTOMER);

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
            //@AuthenticationPrincipal AuthenticatedUser author,
            @RequestBody @Valid InquiryUpdateReqDTO request
    ) {

        // 현재는 인증된 사용자의 ID를 1L로 하드코딩, 실제로는 인증된 사용자 정보를 사용해야 함
        // inquiryCommandService.updateInquiry(request, author.getId());
        inquiryCommandService.updateInquiry(request, 1L);

        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 수정 완료", null));
    }

    /**
     * 문의사항 삭제
     *
     * @param request 요청 DTO
     * @return ResponseEntity<ApiResponse<Void>>
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(
            //@AuthenticationPrincipal AuthenticatedUser author,
            @RequestBody @Valid InquiryDeleteReqDTO request
    ) {
        // 현재는 인증된 사용자의 ID를 1L로 하드코딩, 실제로는 인증된 사용자 정보를 사용해야 함
        // inquiryCommandService.deleteInquiry(inquiryId, author.getId());
        inquiryCommandService.deleteInquiry(request, 1L);

        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 삭제 완료", null));
    }

}
