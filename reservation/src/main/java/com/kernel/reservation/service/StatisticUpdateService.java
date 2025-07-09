package com.kernel.reservation.service;

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
public class StatisticUpdateService {

    /**
     * 매니저 통계 업데이트
     * @param managerStatistic 매니저 통계 엔티티
     * @param count 예약 수 (1 또는 -1)
     * OptimisticLockException 발생 시 재시도 0.05초 간격으로 최대 5회 재시도
     */
    @Retryable(
            value = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 50)
    )
    @Transactional
    public void updateManagerStatistic(ManagerStatistic managerStatistic, Integer count) {
        managerStatistic.updateReservationCount(count);
    }


    /**
     * 고객 통계 업데이트
     * @param customerStatistic 고객 통계 엔티티
     * @param count 예약 수 (1 또는 -1)
     * OptimisticLockException 발생 시 재시도 0.05초 간격으로 최대 5회 재시도
     */
    @Retryable(
            value = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 50)
    )
    @Transactional
    public void updateCustomerStatistic(CustomerStatistic customerStatistic, Integer count) {
        customerStatistic.updateReservationCount(count);
    }

    /**
     * 통계 업데이트 실패 시 복구 메서드 (매니저/고객 통합)
     */
    @Recover
    public void recover(OptimisticLockException e, Object statistic, Integer count) {
        throw new MemberStatisticException(MemberStatisticErrorCode.CONCURRENT_UPDATE_ERROR);
    }
}