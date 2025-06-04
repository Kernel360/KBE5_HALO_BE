package com.kernel.common.customer.service;

import com.kernel.common.customer.dto.request.CustomerFeedbackReqDTO;
import com.kernel.common.customer.dto.response.CustomerFeedbackRspDTO;
import com.kernel.common.customer.dto.response.CustomerFeedbackUpdateRspDTO;
import com.kernel.common.global.enums.FeedbackType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerFeedbackService {

    // 수요자 피드백 검색(feedbackType)
    Page<CustomerFeedbackRspDTO> searchCustomerFeedbackByFeedbackType(Long customerId, FeedbackType feedbackType, Pageable pageable);

    // 수요자 피드백 등록(취소, 타입 변경)
    CustomerFeedbackUpdateRspDTO proceedCustomerFeedback(Long customerId, CustomerFeedbackReqDTO reqDTO);
}
