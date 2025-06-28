package com.kernel.inquiry.controller.inquiry;

import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.service.dto.request.InquiryCreateReqDTO;
import com.kernel.inquiry.service.dto.request.InquiryUpdateReqDTO;
import com.kernel.inquiry.service.inquiry.InquiryCommandService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "문의사항 관리 API", description = "관리자 문의사항 생성, 수정, 삭제 API")
@RestController
@RequestMapping("/api/manager/inquiry/command")
@RequiredArgsConstructor
public class InquiryManagerCommandController {

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
        inquiryCommandService.createInquiry(request, 1L, AuthorType.MANAGER);

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
     * @param inquiryId 삭제할 문의사항 ID
     * @return ResponseEntity<ApiResponse<Void>>
     */
    @DeleteMapping("/{inquiry_id}")
    public ResponseEntity<ApiResponse<Void>> deleteInquiry(
            //@AuthenticationPrincipal AuthenticatedUser author,
            @PathVariable("inquiry_id") Long inquiryId
    ) {
        // 현재는 인증된 사용자의 ID를 1L로 하드코딩, 실제로는 인증된 사용자 정보를 사용해야 함
        // inquiryCommandService.deleteInquiry(inquiryId, author.getId());
        inquiryCommandService.deleteInquiry(inquiryId, 1L);

        return ResponseEntity.ok(new ApiResponse<>(true, "문의사항 삭제 완료", null));
    }

}
