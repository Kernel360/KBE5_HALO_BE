package com.kernel.evaluation.repository.review;

import com.kernel.evaluation.domain.info.CustomerReviewInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCustomerReviewRepository {

    // 수요자 리뷰 내역 조회(페이징)
    Page<CustomerReviewInfo> getCustomerReviews(Long customerId, Pageable pageable);

    // 수요자 리뷰 조회 bz 예약 ID
    CustomerReviewInfo getCustomerReviewsByReservationId(Long customerId, Long reservationId);


}
