package com.kernel.evaluation.controller;


import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.service.feedback.CustomerFeedbackService;
import com.kernel.evaluation.service.feedback.dto.request.CustomerFeedbackReqDTO;
import com.kernel.evaluation.service.feedback.dto.response.CustomerFeedbackRspDTO;
import com.kernel.evaluation.service.feedback.dto.response.CustomerFeedbackUpdateRspDTO;
import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/feedbacks")
@RequiredArgsConstructor
public class CustomerFeedbackController {

    private final CustomerFeedbackService customerFeedbackService;

    /**
     * 수요자 피드백 조회 및 검색
     * @param user 로그인한 유저
     * @param feedbackType 피드백 타입
     * @param pageable 페이징 정보
     * @return 검색된 피드백 목록
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerFeedbackRspDTO>>> searchCustomerFeedbackByFeedbackType(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(required = false) FeedbackType feedbackType,
            @PageableDefault(size=10) Pageable pageable
    ){

        Page<CustomerFeedbackRspDTO> feedbackRspDTOPage
                = customerFeedbackService.searchCustomerFeedbackByFeedbackType(user.getUserId(), feedbackType, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 피드백 조회 성공", feedbackRspDTOPage));
    }

    /**
     * 수요자 피드백 등록(취소, 타입 변경)
     * @param user 로그인한 유저
     * @param reqDTO 피드백 요청 DTO
     * @return 검색된 피드백 목록
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerFeedbackUpdateRspDTO>> proceedCustomerFeedback (
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestBody CustomerFeedbackReqDTO reqDTO
    ){

        CustomerFeedbackUpdateRspDTO feedbackRspDTO
                = customerFeedbackService.proceedCustomerFeedback(user.getUserId(), reqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 피드백 조회 성공", feedbackRspDTO));
    }


}
