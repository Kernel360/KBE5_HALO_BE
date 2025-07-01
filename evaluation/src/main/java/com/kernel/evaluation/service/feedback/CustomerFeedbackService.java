package com.kernel.evaluation.service.feedback;

import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.service.feedback.dto.request.CustomerFeedbackReqDTO;
import com.kernel.evaluation.service.feedback.dto.response.CustomerFeedbackRspDTO;
import com.kernel.evaluation.service.feedback.dto.response.CustomerFeedbackUpdateRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerFeedbackService {

    // 수요자 피드백 검색(feedbackType)
    Page<CustomerFeedbackRspDTO> searchCustomerFeedbackByFeedbackType(Long userId, FeedbackType feedbackType, Pageable pageable);

    // 수요자 피드백 등록(취소, 타입 변경)
    CustomerFeedbackUpdateRspDTO proceedCustomerFeedback(Long userId, CustomerFeedbackReqDTO reqDTO);
}
