package com.kernel.evaluation.repository.feedback;


import com.kernel.evaluation.common.enums.FeedbackType;
import com.kernel.evaluation.domain.info.CustomerFeedbackInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCustomerFeedbackRepository {

    // 수요자 피드백 검색 및 조회(page, feedbackType)
    Page<CustomerFeedbackInfo> searchFeedbackByFeedbackType(Long customerId, FeedbackType feedbackType, Pageable pageable);
}
