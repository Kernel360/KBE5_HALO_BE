package com.kernel.common.manager.service;

import com.kernel.common.manager.dto.request.ManagerReviewReqDTO;
import com.kernel.common.manager.dto.response.ManagerReviewRspDTO;

public interface ManagerReviewService {

    /**
     * 매니저 리뷰 등록
     * @param managerReviewReqDTO
     * @return 작성된 리뷰 정보를 담은 응답
     */
    public ManagerReviewRspDTO createManagerReview(Long authorId, Long reservationId, ManagerReviewReqDTO managerReviewReqDTO);
}
