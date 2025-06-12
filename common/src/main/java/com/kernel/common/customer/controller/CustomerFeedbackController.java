package com.kernel.common.customer.controller;

import com.kernel.common.customer.dto.request.CustomerFeedbackReqDTO;
import com.kernel.common.customer.dto.response.CustomerFeedbackRspDTO;
import com.kernel.common.customer.dto.response.CustomerFeedbackUpdateRspDTO;
import com.kernel.common.customer.service.CustomerFeedbackService;
import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.enums.FeedbackType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/feedbacks")
@RequiredArgsConstructor
public class CustomerFeedbackController {

    private final CustomerFeedbackService customerFeedbackService;

    /**
     * 수요자 피드백 조회 및 검색
     * @param customer 수요자ID
     * @param feedbackType 피드백 타입
     * @param pageable 페이징 정보
     * @return 검색된 피드백 목록
     */
    @GetMapping("{customer-Id}")
    public ResponseEntity<ApiResponse<Page<CustomerFeedbackRspDTO>>> searchCustomerFeedbackByFeedbackType(
            @AuthenticationPrincipal AuthenticatedUser customer,
            @RequestParam(required = false) FeedbackType feedbackType,
            @PageableDefault(size=10) Pageable pageable
    ){

        Page<CustomerFeedbackRspDTO> feedbackRspDTOPage
                = customerFeedbackService.searchCustomerFeedbackByFeedbackType(customer.getUserId(), feedbackType, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 피드백 조회 성공", feedbackRspDTOPage));
    }

    /**
     * 수요자 피드백 등록(취소, 타입 변경)
     * @param customer 수요자ID
     * @param reqDTO 피드백 요청 DTO
     * @return 검색된 피드백 목록
     */
    @PostMapping("{customer-Id}")
    public ResponseEntity<ApiResponse<CustomerFeedbackUpdateRspDTO>> proceedCustomerFeedback (
            @AuthenticationPrincipal AuthenticatedUser customer,
            @RequestBody CustomerFeedbackReqDTO reqDTO
    ){

        CustomerFeedbackUpdateRspDTO feedbackRspDTO
                = customerFeedbackService.proceedCustomerFeedback(customer.getUserId(), reqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 피드백 조회 성공", feedbackRspDTO));
    }


}
