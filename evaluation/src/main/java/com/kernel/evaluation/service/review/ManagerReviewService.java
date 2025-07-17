package com.kernel.evaluation.service.review;


import com.kernel.evaluation.service.review.dto.request.ManagerReviewSearchCondDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewCreateReqDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewUpdateReqDTO;
import com.kernel.evaluation.service.review.dto.response.ManagerReviewPageRspDTO;
import com.kernel.evaluation.service.review.dto.response.ManagerReviewRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerReviewService {

    // 매니저 리뷰 목록 조회 (검색 조건 및 페이징 처리)
    Page<ManagerReviewPageRspDTO> searchManagerReviewsWithPaging(Long managerId, ManagerReviewSearchCondDTO searchCondDTO, Pageable pageable);

    // 매니저 리뷰 등록
    ManagerReviewRspDTO createManagerReview(Long authorId, Long reservationId, ReviewCreateReqDTO managerReviewReqDTO);

    // 매니저 리뷰 수정
    ManagerReviewRspDTO updateManagerReview(Long userId, Long reviewId, ReviewUpdateReqDTO updateReqDTO);
}
