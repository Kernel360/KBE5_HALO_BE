package com.kernel.evaluation.service.review;

import com.kernel.member.common.enums.MemberStatisticErrorCode;
import com.kernel.member.common.exception.MemberStatisticException;
import com.kernel.member.domain.entity.CustomerStatistic;
import com.kernel.member.domain.entity.ManagerStatistic;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EvaluationStatisticUpdateServiceImpl implements EvaluationStatisticUpdateService {

    /**
     * 매니저 리뷰 통계 업데이트
     * @param managerStatistic 매니저 통계 엔티티
     *
     */
    @Retryable(
            value = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 50)
    )
    @Override
    @Transactional
    public void updateManagerReviewStatistic(ManagerStatistic managerStatistic) {
        managerStatistic.updateReviewCount();
    }

    /**
     * 고객 리뷰 통계 업데이트
     * @param customerStatistic 고객 통계 엔티티
     */
    @Retryable(
            value = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 50)
    )
    @Override
    @Transactional
    public void updateCustomerReviewStatistic(CustomerStatistic customerStatistic) {
        customerStatistic.updateReviewCount();
    }

    /**
     * 통계 업데이트 실패 시 복구 메서드 (매니저/고객 통합)
     */
    @Override
    @Recover
    public void recover(OptimisticLockException e, Object statistic) {
        throw new MemberStatisticException(MemberStatisticErrorCode.CONCURRENT_UPDATE_ERROR);
    }
}
