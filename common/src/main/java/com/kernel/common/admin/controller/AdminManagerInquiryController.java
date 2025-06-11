package com.kernel.common.admin.controller;

import com.kernel.common.admin.dto.request.AdminInquiryReplyReqDTO;
import com.kernel.common.admin.dto.request.AdminInquirySearchReqDTO;
import com.kernel.common.admin.dto.response.AdminInquiryDetailRspDTO;
import com.kernel.common.admin.dto.response.AdminInquirySummaryManagerRspDTO;
import com.kernel.common.admin.dto.response.AdminInquirySummaryRspDTO;
import com.kernel.common.admin.service.AdminManagerInquiryService;
import com.kernel.common.global.entity.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/inquiries/manager")
@RequiredArgsConstructor
public class AdminManagerInquiryController {

    private final AdminManagerInquiryService adminManagerInquiryService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminInquirySummaryRspDTO>>> getManagerInquiryPage(
            // @AuthenticationPrincipal AuthenticatedUser admin,
            @ModelAttribute AdminInquirySearchReqDTO request,
            Pageable pageable
    ) {

        // TODO: 관리자 권한 확인 (ROLE_ADMIN 권한을 체크)
        /*if (admin.getAuthorities() == null || !admin.getAuthorities().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }*/

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "inquiryId"));

        Page<AdminInquirySummaryRspDTO> inquiryPage = adminManagerInquiryService.getManagerInquiryPage(request, sortedPageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "고객 문의사항 목록 조회 성공", inquiryPage));
    }

    @GetMapping("/{inquiry-id}")
    public ResponseEntity<ApiResponse<AdminInquiryDetailRspDTO>> getManagerInquiryDetail(
            // @AuthenticationPrincipal AuthenticatedUser admin,
            @PathVariable("inquiry-id") Long inquiryId
    ) {
        // TODO: 관리자 권한 확인 (ROLE_ADMIN 권한을 체크)
        /*if (admin.getAuthorities() == null || !admin.getAuthorities().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }*/

        AdminInquiryDetailRspDTO inquiryDetail = adminManagerInquiryService.getManagerInquiryDetail(inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "고객 문의사항 상세 조회 성공", inquiryDetail));
    }

    @DeleteMapping("/{inquiry-id}")
    public ResponseEntity<ApiResponse<Void>> deleteManagerInquiry(
            // @AuthenticationPrincipal AuthenticatedUser admin,
            @PathVariable("inquiry-id") Long inquiryId
    ) {
        // TODO: 관리자 권한 확인 (ROLE_ADMIN 권한을 체크)
        /*if (admin.getAuthorities() == null || !admin.getAuthorities().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }*/

        adminManagerInquiryService.DeleteManagerInquiry(inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "고객 문의사항 삭제 성공", null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createReplyManagerInquiry(
            // @AuthenticationPrincipal AuthenticatedUser admin,
            @RequestBody @Valid AdminInquiryReplyReqDTO reply
    ) {
        // TODO: 관리자 권한 확인 (ROLE_ADMIN 권한을 체크)
        /*if (admin.getAuthorities() == null || !admin.getAuthorities().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }*/

        //adminCustomerInquiryService.CreateReplyCustomerInquiry(reply, admin.getUserId());
        adminManagerInquiryService.CreateReplyManagerInquiry(reply, 0L);
        return ResponseEntity.ok(new ApiResponse<>(true, "고객 문의사항 답변 생성 성공", null));
    }

    @PatchMapping("/{reply-id}")
    public ResponseEntity<ApiResponse<Void>> updateReplyManagerInquiry(
            // @AuthenticationPrincipal AuthenticatedUser admin,
            @RequestBody @Valid AdminInquiryReplyReqDTO reply,
            @PathVariable("reply-id") Long replyId
    ) {
        // TODO: 관리자 권한 확인 (ROLE_ADMIN 권한을 체크)
        /*if (admin.getAuthorities() == null || !admin.getAuthorities().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }*/

        //adminCustomerInquiryService.UpdateReplyCustomerInquiry(reply, admin.getUserId(), replyId);
        adminManagerInquiryService.UpdateReplyManagerInquiry(reply, 0L, replyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "고객 문의사항 답변 수정 성공", null));
    }
}
