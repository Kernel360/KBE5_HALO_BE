package com.kernel.common.admin.controller;

import com.kernel.common.admin.dto.request.AdminInquiryReplyReqDTO;
import com.kernel.common.admin.dto.request.AdminInquirySearchReqDTO;
import com.kernel.common.admin.dto.response.AdminInquiryDetailRspDTO;
import com.kernel.common.admin.dto.response.AdminInquirySummaryCustomerRspDTO;
import com.kernel.common.admin.service.AdminCustomerInquiryService;
import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/inquiries/customer")
@RequiredArgsConstructor
public class AdminCustomerInquiryController {

    private final AdminCustomerInquiryService adminCustomerInquiryService;

    /**
     * 고객 문의사항 목록 조회 및 검색
     * @param admin 관리자 ID
     * @param request 검색 요청 DTO
     * @param pageable 페이지네이션 정보
     * @return 고객 문의사항 목록 페이지
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminInquirySummaryCustomerRspDTO>>> getCustomerInquiryPage(
            // @AuthenticationPrincipal AuthenticatedUser admin,
            @ModelAttribute @Valid AdminInquirySearchReqDTO request,
            Pageable pageable
    ) {

        // TODO: 관리자 권한 확인 (ROLE_ADMIN 권한을 체크)
        /*if (admin.getAuthorities() == null || !admin.getAuthorities().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }*/

        Page<AdminInquirySummaryCustomerRspDTO> inquiryPage = adminCustomerInquiryService.getCustomerInquiryPage(request, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "고객 문의사항 목록 조회 성공", inquiryPage));
    }

    /**
     * 고객 문의사항 상세 조회
     * @param admin 관리자 ID
     * @param inquiryId 삭제할 문의사항 ID
     * @return 성공 여부
     */
    @GetMapping("/{inquiry-id}")
    public ResponseEntity<ApiResponse<AdminInquiryDetailRspDTO>> getCustomerInquiryDetail(
            // @AuthenticationPrincipal AuthenticatedUser admin,
            @PathVariable("inquiry-id") Long inquiryId
    ) {
        // TODO: 관리자 권한 확인 (ROLE_ADMIN 권한을 체크)
        /*if (admin.getAuthorities() == null || !admin.getAuthorities().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }*/

        AdminInquiryDetailRspDTO inquiryDetail = adminCustomerInquiryService.getCustomerInquiryDetail(inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "고객 문의사항 상세 조회 성공", inquiryDetail));
    }

    /**
     * 고객 문의사항 삭제
     * @param admin 관리자 ID
     * @param inquiryId 삭제할 문의사항 ID
     * @return 성공 여부
     */
    @DeleteMapping("/{inquiry-id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomerInquiry(
            // @AuthenticationPrincipal AuthenticatedUser admin,
            @PathVariable("inquiry-id") Long inquiryId
    ) {
        // TODO: 관리자 권한 확인 (ROLE_ADMIN 권한을 체크)
        /*if (admin.getAuthorities() == null || !admin.getAuthorities().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }*/

        adminCustomerInquiryService.DeleteCustomerInquiry(inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "고객 문의사항 삭제 성공", null));
    }

    /**
     * 고객 문의사항 답변 생성
     * @param admin 관리자 ID
     * @param reply 답변 요청 DTO
     * @return 성공 여부
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createReplyCustomerInquiry(
            // @AuthenticationPrincipal AuthenticatedUser admin,
            @RequestBody @Valid AdminInquiryReplyReqDTO reply
    ) {
        // TODO: 관리자 권한 확인 (ROLE_ADMIN 권한을 체크)
        /*if (admin.getAuthorities() == null || !admin.getAuthorities().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }*/

        //adminCustomerInquiryService.CreateReplyCustomerInquiry(reply, admin.getUserId());
        adminCustomerInquiryService.CreateReplyCustomerInquiry(reply, 0L);
        return ResponseEntity.ok(new ApiResponse<>(true, "고객 문의사항 답변 생성 성공", null));
    }

    /**
     * 고객 문의사항 답변 수정
     * @param admin 관리자 ID
     * @param reply 수정 요청 DTO
     * @param replyId 수정할 답변 ID
     * @return 성공 여부
     */
    @PatchMapping("/{reply-id}")
    public ResponseEntity<ApiResponse<Void>> updateReplyCustomerInquiry(
            // @AuthenticationPrincipal AuthenticatedUser admin,
            @RequestBody @Valid AdminInquiryReplyReqDTO reply,
            @PathVariable("reply-id") Long replyId
    ) {
        // TODO: 관리자 권한 확인 (ROLE_ADMIN 권한을 체크)
        /*if (admin.getAuthorities() == null || !admin.getAuthorities().contains("ROLE_ADMIN")) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "권한이 없습니다.", null));
        }*/

        //adminCustomerInquiryService.UpdateReplyCustomerInquiry(reply, admin.getUserId(), replyId);
        adminCustomerInquiryService.UpdateReplyCustomerInquiry(reply, 0L, replyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "고객 문의사항 답변 수정 성공", null));
    }
}
