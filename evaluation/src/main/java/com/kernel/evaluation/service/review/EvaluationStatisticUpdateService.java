package com.kernel.evaluation.service.review;

import com.kernel.evaluation.service.review.dto.request.ReviewCreateReqDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewUpdateReqDTO;
import com.kernel.member.domain.entity.CustomerStatistic;
import com.kernel.member.domain.entity.ManagerStatistic;
import jakarta.persistence.OptimisticLockException;

public interface EvaluationStatisticUpdateService {

    // 매니저 평균 별점, 리뷰 개수 업데이트
    void updateManagerReviewStatisticOnCreate(ManagerStatistic managerStatistic, ReviewCreateReqDTO createReqDTO);

    // 매니저 평균 별점 업데이트
    void updateManagerReviewStatisticOnUpdate(ManagerStatistic managerStatistic, ReviewUpdateReqDTO updateReqDTO);

    // 수요자 평균 별점, 리뷰 개수 업데이트
    void updateCustomerReviewStatisticOnCreate(CustomerStatistic customerStatistic, ReviewCreateReqDTO createReqDTO);

    // 수요자 평균 별점 업데이트
    void updateCustomerReviewStatisticOnUpdate(CustomerStatistic customerStatistic, ReviewUpdateReqDTO updateReqDTO);

    void recover(OptimisticLockException e, Object statistic);
}
