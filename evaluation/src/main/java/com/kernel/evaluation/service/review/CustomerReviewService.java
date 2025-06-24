package com.kernel.evaluation.service.review;

import com.kernel.evaluation.service.review.dto.request.ReviewCreateReqDTO;
import com.kernel.evaluation.service.review.dto.request.ReviewUpdateReqDTO;
import com.kernel.evaluation.service.review.dto.response.CustomerReviewRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerReviewService {

    // 수요자 리뷰 내역 조회
    Page<CustomerReviewRspDTO> getCustomerReviews(Long customerId, Pageable pageable);

    // 수요자 리뷰 조회 by 예약ID
    CustomerReviewRspDTO getCustomerReviewsByReservationId(Long customerId, Long reservationId);

    // 수요자 리뷰 등록
    CustomerReviewRspDTO createCustomerReview(Long userId, Long reservationId, ReviewCreateReqDTO createReqDTO);

    // 수요자 리뷰 수정
    CustomerReviewRspDTO updateCustomerReview(Long userId, Long reviewId, ReviewUpdateReqDTO updateReqDTO);

}
