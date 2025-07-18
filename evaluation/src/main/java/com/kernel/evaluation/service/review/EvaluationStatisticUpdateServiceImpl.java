package com.kernel.evaluation.service.review;

import com.kernel.evaluation.service.review.dto.request.ReviewCreateReqDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewUpdateReqDTO;
import com.kernel.member.common.enums.MemberStatisticErrorCode;
import com.kernel.member.common.exception.MemberStatisticException;
import com.kernel.member.domain.entity.CustomerStatistic;
import com.kernel.member.domain.entity.ManagerStatistic;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class EvaluationStatisticUpdateServiceImpl implements EvaluationStatisticUpdateService {

    /**
     * 수요자 리뷰 등록 시, 매니저 리뷰 평점, 개수 업데이트
     * @param managerStatistic 매니저 통계 엔티티
     * @param createReqDTO 등록 리뷰
     */
    @Retryable(
            value = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 50)
    )
    @Override
    @Transactional
    public void updateManagerReviewStatisticOnCreate(ManagerStatistic managerStatistic, ReviewCreateReqDTO createReqDTO) {


        BigDecimal newAvg = calculateNewAverage(
                managerStatistic.getAverageRating(),
                managerStatistic.getReviewCount(),
                BigDecimal.valueOf(createReqDTO.getRating())
        );

        managerStatistic.updateAverageRating(newAvg);
        managerStatistic.updateReviewCount();
        
    }

    /**
     * 수요자 리뷰 수정 시, 매니저 리뷰 평점 업데이트
     * @param managerStatistic 매니저 통계 엔티티
     * @param createReqDTO 등록 리뷰
     */
    @Override
    public void updateManagerReviewStatisticOnUpdate(ManagerStatistic managerStatistic, ReviewUpdateReqDTO createReqDTO) {

        BigDecimal newAvg = calculateNewAverage(
                managerStatistic.getAverageRating(),
                managerStatistic.getReviewCount(),
                BigDecimal.valueOf(createReqDTO.getRating())
        );

        managerStatistic.updateAverageRating(newAvg);
    }

    /**
     * 매니저 리뷰 등록 시, 수요자 리뷰 평점, 개수 업데이트
     * @param customerStatistic 고객 통계 엔티티
     */
    @Retryable(
            value = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 50)
    )
    @Override
    @Transactional
    public void updateCustomerReviewStatisticOnCreate(CustomerStatistic customerStatistic,ReviewCreateReqDTO createReqDTO) {

        BigDecimal newAvg = calculateNewAverage(
                customerStatistic.getAverageRating(),
                customerStatistic.getReviewCount(),
                BigDecimal.valueOf(createReqDTO.getRating())
        );

        customerStatistic.updateAverageRating(newAvg);
        customerStatistic.updateReviewCount();

    }

    /**
     * 매니저 리뷰 수정 시, 수요자 리뷰 평점 업데이트
     * @param customerStatistic 매니저 통계 엔티티
     * @param updateReqDTO 등록 리뷰
     */
    @Retryable(
            value = OptimisticLockException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 50)
    )
    @Override
    @Transactional
    public void updateCustomerReviewStatisticOnUpdate(CustomerStatistic customerStatistic, ReviewUpdateReqDTO updateReqDTO) {

        BigDecimal newAvg = calculateNewAverage(
                customerStatistic.getAverageRating(),
                customerStatistic.getReviewCount(),
                BigDecimal.valueOf(updateReqDTO.getRating())
        );

        customerStatistic.updateAverageRating(newAvg);

    }

    /**
     * 평점 계산 공통 메서드
     * @param currentAvg 현재 평균 평점
     * @param currentCount 현재 리뷰 개수
     * @param newRating 새로운 평점
     * @return 계산된 새로운 평균 평점
     */
    private BigDecimal calculateNewAverage(BigDecimal currentAvg, int currentCount, BigDecimal newRating) {

        BigDecimal result = (currentAvg.multiply(BigDecimal.valueOf(currentCount))
                .add(newRating))
                .divide(BigDecimal.valueOf(currentCount + 1), 2, RoundingMode.HALF_UP);
                
        return result;
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
