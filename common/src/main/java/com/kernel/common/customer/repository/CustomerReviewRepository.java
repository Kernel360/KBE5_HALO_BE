package com.kernel.common.customer.repository;

import com.kernel.common.reservation.entity.Review;
import com.kernel.common.global.enums.AuthorType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReviewRepository extends JpaRepository<Review, Long>, CustomCustomerReviewRepository {

    // 리뷰 조회(where reservationId, authorId, authorType)
    Review findByReservation_ReservationIdAndAuthorIdAndAuthorType(Long reservationId, Long customerId, AuthorType authorType);
}
