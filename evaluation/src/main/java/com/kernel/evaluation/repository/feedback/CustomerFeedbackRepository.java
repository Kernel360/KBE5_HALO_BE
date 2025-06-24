package com.kernel.evaluation.repository.feedback;

import com.kernel.evaluation.domain.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerFeedbackRepository extends JpaRepository<Feedback, Long>, CustomCustomerFeedbackRepository {

    // 피드백 조회(where feedbackId, customerId, managerId)
    Optional<Feedback> findByCustomer_CustomerIdAndManager_ManagerId(Long customerId, Long managerId);
}
