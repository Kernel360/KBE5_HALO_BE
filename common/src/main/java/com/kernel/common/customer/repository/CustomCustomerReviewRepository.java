package com.kernel.common.customer.repository;

import com.kernel.common.customer.dto.response.CustomerReviewRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomCustomerReviewRepository {

    // 수요자 리뷰 내역 조회(페이징)
    Page<CustomerReviewRspDTO> getCustomerReviews(Long customerId, Pageable pageable);

    // 수요자 리뷰 조회 by 예약ID
    CustomerReviewRspDTO getCustomerReviewsByReservationId(Long customerId, Long reservationId);
}
