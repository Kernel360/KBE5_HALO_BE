package com.kernel.common.customer.service;

import com.kernel.common.customer.dto.request.CustomerReviewReqDTO;
import com.kernel.common.customer.dto.response.CustomerReviewRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerReviewService {

    // 수요자 리뷰 내역 조회
    Page<CustomerReviewRspDTO> getCustomerReviews(Long customerId, Pageable pageable);

    // 수요자 리뷰 조회 by 예약ID
    CustomerReviewRspDTO getCustomerReviewsByReservationId(Long customerId, Long reservationId);

    // 수요자 리뷰 등록/수정
    CustomerReviewRspDTO createOrUpdateCustomerReview(Long userId, Long reservationId, CustomerReviewReqDTO reviewReqDTO);

}
