package com.kernel.common.manager.controller;


import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.dto.request.ManagerInquiryCreateReqDTO;
import com.kernel.common.manager.dto.request.ManagerInquirySearchCondDTO;
import com.kernel.common.manager.dto.request.ManagerInquiryUpdateReqDTO;
import com.kernel.common.manager.dto.response.ManagerInquiryRspDTO;
import com.kernel.common.manager.dto.response.ManagerInquirySummaryRspDTO;
import com.kernel.common.manager.service.ManagerInquiryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers/inquiries")
public class ManagerInquiryController {

    private final ManagerInquiryService managerInquiryService;

    /**
     * 매니저 문의사항 목록 조회 API (검색 조건 및 페이징 처리)
     * @param manager 매니저
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 검색 조건에 따른 매니저 문의사항 목록을 응답 (페이징 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ManagerInquirySummaryRspDTO>>> searchManagerInquiries(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @ModelAttribute ManagerInquirySearchCondDTO searchCondDTO,
        Pageable pageable
    ) {
        if (    !UserStatus.ACTIVE.equals(manager.getStatus())               // 활성
             && !UserStatus.PENDING.equals(manager.getStatus())              // 대기
             && !UserStatus.REJECTED.equals(manager.getStatus())             // 매니저 승인 거절
             && !UserStatus.TERMINATION_PENDING.equals(manager.getStatus())  // 매니저 계약 해지 대기
        ) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")" );
        }
        Page<ManagerInquirySummaryRspDTO> summaryRspDTOPage
            = managerInquiryService.searchManagerinquiriesWithPaging(manager.getUserId(), searchCondDTO, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 상담 게시글 목록 조회 성공", summaryRspDTOPage));
    }

    /**
     * 매니저 문의사항 상세조회 API
     * @param manager 매니저
     * @param inquiryId 게시글ID
     * @return 매니저 문의사항 상세 정보를 담은 응답
     */
    @GetMapping("/{inquiry-id}")
    public ResponseEntity<ApiResponse<ManagerInquiryRspDTO>> getManagerInquiry(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @PathVariable("inquiry-id") Long inquiryId
    ) {
        if (   !UserStatus.ACTIVE.equals(manager.getStatus())               // 활성
            && !UserStatus.PENDING.equals(manager.getStatus())              // 대기
            && !UserStatus.REJECTED.equals(manager.getStatus())             // 매니저 승인 거절
            && !UserStatus.TERMINATION_PENDING.equals(manager.getStatus())  // 매니저 계약 해지 대기
        ) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")" );
        }
        ManagerInquiryRspDTO rspDTO = managerInquiryService.getManagerInquiry(manager.getUserId(), inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 문의사항 상세 조회 성공", rspDTO));
    }

    /**
     * 매니저 문의사항 등록 API
     * @param manager 매니저
     * @param requestDTO 매니저 문의사항 등록 요청 데이터
     * @return 작성된 매니저 문의사항 정보를 담은 응답
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ManagerInquirySummaryRspDTO>> createManagerInquiry(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @Valid @RequestBody ManagerInquiryCreateReqDTO requestDTO
    ) {
        if (   !UserStatus.ACTIVE.equals(manager.getStatus())               // 활성
            && !UserStatus.PENDING.equals(manager.getStatus())              // 대기
            && !UserStatus.REJECTED.equals(manager.getStatus())             // 매니저 승인 거절
            && !UserStatus.TERMINATION_PENDING.equals(manager.getStatus())  // 매니저 계약 해지 대기
        ) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")" );
        }
        ManagerInquirySummaryRspDTO summaryRspDTO = managerInquiryService.createManagerInquiry(manager.getUserId(), requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 문의사항 등록 성공", summaryRspDTO));
    }

    /**
     * 매니저 문의사항 수정 API
     * @param manager 매니저
     * @param inquiryId 게시글 ID
     * @param requestDTO 매니저 문의사항 수정 요청 데이터
     * @return 수정된 매니저 문의사항 정보를 담은 응답
     */
    @PatchMapping("/{inquiry-id}")
    public ResponseEntity<ApiResponse<ManagerInquirySummaryRspDTO>> updateManagerInquiry(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @PathVariable("inquiry-id") Long inquiryId,
        @Valid @RequestBody ManagerInquiryUpdateReqDTO requestDTO
    ) {
        if (   !UserStatus.ACTIVE.equals(manager.getStatus())               // 활성
            && !UserStatus.PENDING.equals(manager.getStatus())              // 대기
            && !UserStatus.REJECTED.equals(manager.getStatus())             // 매니저 승인 거절
            && !UserStatus.TERMINATION_PENDING.equals(manager.getStatus())  // 매니저 계약 해지 대기
        ) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")" );
        }
        ManagerInquirySummaryRspDTO summaryRspDTO = managerInquiryService.updateManagerInquiry(manager.getUserId(), inquiryId, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 문의사항 수정 성공", summaryRspDTO));
    }

    /**
     * 매니저 문의사항 삭제 API
     * @param manager 매니저
     * @param inquiryId 게시글 ID
     * @return null
     */
    @DeleteMapping("/{inquiry-id}")
    public ResponseEntity<ApiResponse<ManagerInquirySummaryRspDTO>> updateManagerInquiry(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @PathVariable("inquiry-id") Long inquiryId
    ) {
        if (   !UserStatus.ACTIVE.equals(manager.getStatus())               // 활성
            && !UserStatus.PENDING.equals(manager.getStatus())              // 대기
            && !UserStatus.REJECTED.equals(manager.getStatus())             // 매니저 승인 거절
            && !UserStatus.TERMINATION_PENDING.equals(manager.getStatus())  // 매니저 계약 해지 대기
        ) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")" );
        }
        managerInquiryService.deleteManagerInquiry(manager.getUserId(), inquiryId);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 문의사항 삭제 성공", null));
    }
}
