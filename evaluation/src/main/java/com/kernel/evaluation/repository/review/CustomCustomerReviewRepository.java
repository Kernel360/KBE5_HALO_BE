package com.kernel.evaluation.repository.review;

import com.kernel.evaluation.domain.info.CustomerReviewInfo;
import com.kernel.evaluation.service.review.dto.request.ReviewSearchReqDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCustomerReviewRepository {

    // 수요자 리뷰 내역 전체 조회(페이징)
    Page<CustomerReviewInfo> getCustomerReviewsAll(Long customerId, Pageable pageable);

    // 수요자 리뷰 내역 조회 by 별점(페이징)
    Page<CustomerReviewInfo> getCustomerReviewsByRating(Long userId, ReviewSearchReqDTO searchReqDTO, Pageable pageable);

    // 수요자 작성 필요 리뷰 조회(페이징)
    Page<CustomerReviewInfo> getCustomerReviewsNotWritten(Long userId, Pageable pageable);

    // 수요자 리뷰 조회 by 예약 ID
    CustomerReviewInfo getCustomerReviewsByReservationId(Long customerId, Long reservationId);

}
