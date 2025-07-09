package com.kernel.evaluation.service.review;

import com.kernel.member.domain.entity.CustomerStatistic;
import com.kernel.member.domain.entity.ManagerStatistic;
import jakarta.persistence.OptimisticLockException;

public interface EvaluationStatisticUpdateService {

    void updateManagerReviewStatistic(ManagerStatistic managerStatistic);

    void updateCustomerReviewStatistic(CustomerStatistic customerStatistic);

    void recover(OptimisticLockException e, Object statistic);
}
