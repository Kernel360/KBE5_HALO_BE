package com.kernel.common.customer.repository;

import com.kernel.common.customer.dto.response.CustomerFeedbackRspDTO;
import com.kernel.common.global.enums.FeedbackType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCustomerFeedbackRepository {

    // 수요자 피드백 검색 및 조회(page, feedbackType)
    Page<CustomerFeedbackRspDTO> searchCustomerFeedbackByFeedbackType(Long customerId, FeedbackType feedbackType, Pageable pageable);
}
