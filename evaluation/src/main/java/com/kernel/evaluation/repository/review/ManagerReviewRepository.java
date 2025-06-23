package com.kernel.evaluation.repository.review;


import com.kernel.evaluation.common.enums.AuthorType;
import com.kernel.evaluation.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerReviewRepository extends JpaRepository<Review, Long>, CustomManagerReviewRepository {

    // 리뷰 존재 여부 확인(작성자 유형, 작성자ID(=매니저ID), 예약ID 기준)
    Boolean existsByAuthorTypeAndAuthorIdAndReservation_ReservationId(AuthorType authorType, Long managerId, Long reservationId);

}
